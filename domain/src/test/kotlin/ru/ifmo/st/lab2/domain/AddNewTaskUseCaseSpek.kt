package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.gherkin.Feature
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.util.*

object AddNewTaskUseCaseSpek : Spek({
    val sampleTask = Task("Sample task", "Kek", Calendar.getInstance().time, listOf("test", "sample"))
    val taskDBGatewayMock by memoized(CachingMode.GROUP) {
        mock<TaskDBGateway> {}
    }

    val useCase: AddNewTaskUseCase by memoized(CachingMode.GROUP) { AddNewTaskUseCaseImpl(taskDBGatewayMock) }
    Feature("AddNewTaskUseCase") {
        Scenario("adding new task") {
            When("add new task") {
                useCase.addNewTask(sampleTask)
            }

            Then("useCase should be call gateway method") {
                verify(taskDBGatewayMock).addTask(sampleTask)
            }
        }
    }
})
