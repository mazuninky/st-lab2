package ru.ifmo.st.lab2.data

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.io.File
import java.io.FileOutputStream
import java.sql.Date
import java.text.SimpleDateFormat

class DiskTaskDBGateway(private val dbPath: String = defaultDBPath) : TaskDBGateway {
    companion object {
        private const val defaultDBPath = "./taskDB.db"
    }

    override fun addTask(task: Task) {
        val db = File(dbPath)
        if (!db.exists())
            db.createNewFile()

        FileOutputStream(db, true).bufferedWriter().use { writer ->
            writer.apply {
                appendln(task.name)
                appendln(task.description)
                appendln(task.dueData.time.toString())
                appendln(task.tags.size.toString())
                task.tags.forEach { tag ->
                    appendln(tag)
                }
            }
        }
    }

    override fun fetchTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        File(dbPath).bufferedReader().use { reader ->
            val lines = reader.readLines()
            var line = 0

            while (line < lines.size) {
                val name = lines[line]
                val description = lines[line + 1]
                val dueData = Date(lines[line + 2].toLong())
                val count = lines[line + 3].toInt()

                val tags = mutableListOf<String>()
                for (i in 0 until count) {
                    tags.add(lines[line + 4 + i])
                }

                tasks.add(Task(name, description, dueData, tags))

                line += 3 + count + 1
            }
        }

        return tasks
    }
}