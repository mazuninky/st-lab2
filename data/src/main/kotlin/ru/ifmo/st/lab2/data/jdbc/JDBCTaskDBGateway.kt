package ru.ifmo.st.lab2.data.jdbc

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.sql.ResultSet

class JDBCTaskDBGateway(db: DB) : TaskDBGateway {
    private val connection = db.connection

    override fun addTask(task: Task) {
        check(task.id == null)
        val query = "SELECT add_task('${task.name}', '${task.description}', '${task.dueData}', ARRAY[${task.tags.toSQLList()}]);"
        connection.createStatement().use { it.execute(query) }
    }

    private fun List<String>.toSQLList() = joinToString(separator = ",") { "'$it'" }

    private fun ResultSet.fetchTask(): Task {
        val id = getLong("id")
        val name = getString("name")
        val description = getString("description")
        val dueDate = getDate("due_date")
        val tags = getArray("array_agg").array as Array<String>

        return Task(name, description, dueDate, tags.toList(), id)
    }

    override fun fetchTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val query = "SELECT * FROM task_view;"
        connection.createStatement().use {
            val resultSet = it.executeQuery(query)
            while (resultSet.next()) {
                tasks.add(resultSet.fetchTask())
            }
        }

        return tasks
    }

    override fun containsTask(id: Long): Boolean {
//        val query = "SELECT * FROM task WHERE id = $id LIMIT 1;"
//        val isExits
//        connection.createStatement().use {
//            val result = it.executeQuery(query)
//            isExits = result.next()
//        }
//
        val query = "SELECT * FROM task WHERE id = $id LIMIT 1;"
        var isExits = false
        connection.createStatement().use {
            val result = it.executeQuery(query)
            isExits = result.next()
        }
        return isExits
    }

    override fun clear() {
        val query = "DELETE FROM task; DELETE FROM tag;"
        connection.createStatement().use {
            it.execute(query)
        }
    }

    override fun update(task: Task) {
        val updateQuery = "UPDATE task SET name = '${task.name}', description = '${task.description}', due_date = '${task.dueData}' WHERE id = ${task.id};"
        val deleteTagsQuery = "DELETE FROM task_tag WHERE task_id = ${task.id};"
        val tagQuery = "SELECT add_tags_to_task('${task.id}', ARRAY[${task.tags.toSQLList()}]);"
        connection.createStatement().use {
            it.execute(updateQuery)
            it.execute(deleteTagsQuery)
            it.execute(tagQuery)
        }
    }

    override fun findTaskByIdOrName(value: String): Task? {
        var task: Task? = null
        val query = "SELECT * FROM task_view WHERE id = $value OR name = $value;"
        connection.createStatement().use {
            val resultSet = it.executeQuery(query)
            if (resultSet.fetchSize != 1)
                return@use

            resultSet.next()

            task = resultSet.fetchTask()
        }

        return task
    }

    override fun deleteTask(task: Task) {
        val query = "DELETE FROM task WHERE id = ${task.id};"
        connection.createStatement().use {
            it.execute(query)
        }
    }
}
