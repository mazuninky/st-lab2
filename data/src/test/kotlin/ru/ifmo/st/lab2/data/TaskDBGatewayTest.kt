package ru.ifmo.st.lab2.data

import org.amshove.kluent.shouldEqual
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.data.jdbc.DB
import ru.ifmo.st.lab2.data.jdbc.JDBCTaskDBGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.io.File
import java.util.*

object TaskDBGatewayTest : Spek({
    val gateway: TaskDBGateway by memoized { JDBCTaskDBGateway(DB()) }

    val sampleTask = Task("Sample task", "Kek", Calendar.getInstance().time, listOf("test", "sample"))
    val sampleTaskTwo = Task("Sample task 2", "Kek 2", Calendar.getInstance().time, listOf("test", "sample", "second"))
    val sampleTaskThrid = Task("Sample task 3", "Kek 3", Calendar.getInstance().time, listOf("test", "sample", "thrid"))
    val tasks = listOf(sampleTask, sampleTaskTwo, sampleTaskThrid)

    Feature("DiskTaskDBGateway task adding") {

        //        Scenario("Work with one task") {
//            Given("empty db") {
//                gateway.clear()
//            }
//
//            When("Add sample task") {
//                gateway.addTask(sampleTask)
//            }
//
//            Then("db should be contains one task") {
//                gateway.fetchTasks().size shouldEqual 1
//            }
//
//            Then("db should be contains sample task") {
//                gateway.fetchTasks().first() shouldEqual sampleTask
//            }
//        }

//        Scenario("Work with multiple tasks") {
//            Given("empty db") {
//                gateway.clear()
//            }
//
//            When("add list of taks") {
//                tasks.forEach { gateway.addTask(it) }
//            }
//
//            Then("db should be contains ${tasks.size} tasks") {
//                gateway.fetchTasks().size shouldEqual tasks.size
//            }
//
//            Then("db should be contains list of tasks") {
//                gateway.fetchTasks() shouldEqual tasks
//            }
//        }
    }
})
