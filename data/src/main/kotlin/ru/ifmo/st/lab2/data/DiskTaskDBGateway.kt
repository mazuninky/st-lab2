package ru.ifmo.st.lab2.data

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.io.File
import java.io.FileOutputStream
import java.sql.Date
import java.text.SimpleDateFormat

class DiskTaskDBGateway(dbPath: String = defaultDBPath) : TaskDBGateway {

    private val dbFile = File(dbPath)
    private val metaFile = File("$dbPath.meta")

    companion object {
        private const val defaultDBPath = "./taskDB.db"
    }

    override fun addTask(task: Task) {
        createFilesIfNotExists()

        val lineNumber = getIndexFromMeta()

        if (task.id != null)
            throw IllegalStateException()

        FileOutputStream(dbFile, true).bufferedWriter().use { writer ->
            writer.apply {
                appendln(lineNumber.toString())
                appendln(task.name)
                appendln(task.description)
                appendln(task.dueData.time.toString())
                appendln(task.tags.size.toString())
                task.tags.forEach { tag ->
                    appendln(tag)
                }
            }
        }

        FileOutputStream(metaFile, false).bufferedWriter().use { writer ->
            writer.write(lineNumber.inc().toString())
        }
    }

    override fun addAll(tasks: List<Task>) {
        createFilesIfNotExists()

        var lineNumber = getIndexFromMeta()

        FileOutputStream(dbFile, true).bufferedWriter().use { writer ->
            writer.apply {
                tasks.forEach { task ->
                    if (task.id != null)
                        throw IllegalStateException()

                    appendln(lineNumber.toString())
                    appendln(task.name)
                    appendln(task.description)
                    appendln(task.dueData.time.toString())
                    appendln(task.tags.size.toString())
                    task.tags.forEach { tag ->
                        appendln(tag)
                    }

                    lineNumber++
                }

            }
        }

        FileOutputStream(metaFile, false).bufferedWriter().use { writer ->
            writer.write(lineNumber.toString())
        }
    }

    private fun getIndexFromMeta() = metaFile.readText().toIntOrNull() ?: 0

    private fun createFilesIfNotExists() {
        if (!dbFile.exists()) {
            dbFile.createNewFile()
            metaFile.createNewFile()
        }
    }

    override fun fetchTasks(): List<Task> {
        val tasks = mutableListOf<Task>()

        if (!dbFile.exists())
            return tasks

        dbFile.bufferedReader().use { reader ->
            val lines = reader.readLines()
            var line = 0

            while (line < lines.size) {
                val id = lines[line].toLong()
                val name = lines[line + 1]
                val description = lines[line + 2]
                val dueData = Date(lines[line + 3].toLong())
                val count = lines[line + 4].toInt()

                val tags = mutableListOf<String>()
                for (i in 0 until count) {
                    tags.add(lines[line + 5 + i])
                }

                tasks.add(Task(name, description, dueData, tags, id))

                line += 4 + count + 1
            }
        }

        return tasks
    }

    override fun clear() {
        dbFile.deleteIfExists()
        metaFile.deleteIfExists()
    }
}


fun File.deleteIfExists() {
    if (exists())
        delete()
}
