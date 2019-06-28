package ru.ifmo.st.lab2.data

import org.amshove.kluent.shouldEqual
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.data.jdbc.DB
import ru.ifmo.st.lab2.data.jdbc.JDBCTaskDBGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.io.File
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.*

object TaskDBGatewayTest : Spek({
    val db = DB()
    val gateway: TaskDBGateway by memoized { JDBCTaskDBGateway(db) }

    val sampleTask = Task("Sample task", "Kek", Calendar.getInstance().time, listOf("test", "sample"))
    val sampleTaskTwo = Task("Sample task 2", "Kek 2", Calendar.getInstance().time, listOf("test", "sample", "second"))
    val sampleTaskThrid = Task("Sample task 3", "Kek 3", Calendar.getInstance().time, listOf("test", "sample", "thrid"))
    val tasks = listOf(sampleTask, sampleTaskTwo, sampleTaskThrid)

    Feature("DiskTaskDBGateway task adding") {

        Scenario("Add task") {
            Given("empty task table") {
                db.executeStatement {
                    it.execute("DELETE FROM task;")
                }
            }


            When("add task") {
                gateway.addTask(sampleTask)
            }


            Then("should add task to db") {
                db.executeStatement {
                    val result = it.executeQuery("SELECT * FROM task WHERE name = '${sampleTask.name}' AND description = '${sampleTask.description}';")
                    result.next() shouldEqual true
                }
            }
        }

        Scenario("Get task") {
            Given("empty task table") {
                db.executeStatement {
                    it.execute("DELETE FROM task; DELETE FROM tag;")
                }
            }


            var insertedId: Long = 0

            When("add task with sql statement") {
                insertedId = db.connection.addTask(sampleTask)
            }

            When("add tags to task with sql statement") {
                sampleTask.tags.forEach {
                    val tagId = db.connection.addTag(it)
                    db.connection.addTaskTagConnection(insertedId, tagId)
                }
            }


            Then("should get only sample task for db") {
                val tasks = gateway.fetchTasks()
                tasks.size shouldEqual 1
                val task = tasks.first()
                task.name shouldEqual sampleTask.name
                task.description shouldEqual sampleTask.description
                task.tags shouldEqual sampleTask.tags
            }
        }
    }
})


fun Connection.addTask(task: Task): Long {
    val sql = "INSERT INTO task VALUES (DEFAULT, '${task.name}', '${task.description}', '${task.dueData}');"
    val preparedStatement = prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
    preparedStatement.executeUpdate()
    val keys = preparedStatement.generatedKeys
    return if (keys.next()) {
        keys.getLong(1)
    } else {
        throw  SQLException("Creating user failed, no ID obtained.");
    }
}


fun Connection.addTag(tag: String): Long {
    val sql = "INSERT INTO tag VALUES (DEFAULT, '$tag');"
    val preparedStatement = prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
    preparedStatement.executeUpdate()
    val keys = preparedStatement.generatedKeys
    return if (keys.next()) {
        keys.getLong(1)
    } else {
        throw  SQLException("Creating user failed, no ID obtained.");
    }
}

fun Connection.addTaskTagConnection(taskId: Long, tagId: Long) {
    createStatement().use {
        it.execute("INSERT INTO task_tag VALUES ($taskId, $tagId);")
    }
}
