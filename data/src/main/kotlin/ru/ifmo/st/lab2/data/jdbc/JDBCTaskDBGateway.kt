package ru.ifmo.st.lab2.data.jdbc

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.core.TaskState
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.sql.ResultSet

class JDBCTaskDBGateway(db: DB) : TaskDBGateway {
    private val connection = db.connection

    override fun addTask(task: Task) {
        check(task.id == null)
        val query =
            "SELECT add_task('${task.name}', '${task.description}', '${task.dueData}', ARRAY[${task.tags.toSQLList()}]);"
        connection.createStatement().use { it.execute(query) }
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
        val updateQuery =
            "UPDATE task SET name = '${task.name}', description = '${task.description}', due_date = '${task.dueData}' WHERE id = ${task.id};"
        val deleteTagsQuery = "DELETE FROM task_tag WHERE task_id = ${task.id};"
        val updateStateQuery = "UPDATE task_state SET state_id = ${task.state.toId()} WHERE task_id = ${task.id};"
        val tagQuery = "SELECT add_tags_to_task('${task.id}', ARRAY[${task.tags.toSQLList()}]);"
        connection.createStatement().use {
            it.execute(updateQuery)
            it.execute(deleteTagsQuery)
            it.execute(tagQuery)
            it.execute(updateStateQuery)
        }
    }

    override fun findTaskByIdOrName(value: String): Task? {
        var task: Task? = null
        val isNumber = value.toIntOrNull() != null
        val query = if (isNumber)
            "SELECT * FROM task_view WHERE id = $value::INTEGER OR name = '$value';"
        else
            "SELECT * FROM task_view WHERE name = '$value';"
        connection.createStatement().use {
            val resultSet = it.executeQuery(query)

            if (!resultSet.next()) {
                task = null
                return@use
            }

            task = resultSet.fetchTask()

            if (resultSet.next()) {
                task = null
            }
        }

        return task
    }

    override fun deleteTask(task: Task) {
        val query = "DELETE FROM task WHERE id = ${task.id};"
        connection.createStatement().use {
            it.execute(query)
        }
    }


    override fun fetchTags(): List<String> {
        val tags = mutableListOf<String>()
        val query = "SELECT text FROM tag;"
        connection.createStatement().use {
            val resultSet = it.executeQuery(query)
            while (resultSet.next()) {
                tags.add(resultSet.getString("text"))
            }
        }

        return tags
    }

    override fun findTasksByTags(tags: List<String>): List<Task> {
        val tasks = mutableListOf<Task>()
        val query = "SELECT * FROM tag NATURAL JOIN task_tag NATURAL JOIN task WHERE tag.text in (${tags.toSQLList()});"
        connection.createStatement().use {
            val resultSet = it.executeQuery(query)
            while (resultSet.next()) {
                tasks.add(resultSet.fetchTask())
            }
        }

        return tasks
    }
}
