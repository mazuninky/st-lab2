package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.amshove.kluent.shouldBe
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.gherkin.Feature
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object FindTaskByTagUseCaseSpek : Spek({
    val currentTime = Calendar.getInstance().time

    val tags = listOf("test", "collection")

    val tasks = listOf(
            Task("Sample1", "Description1", currentTime, listOf("kek", "top")),
            Task("Sample2", "Description2", currentTime),
            Task("Sample3", "Description3", currentTime, listOf("test")),
            Task("Sample4", "Description4", currentTime, listOf("collection of tips", "tip")),
            Task("Sample5", "Description5", currentTime)
    )

    val expectedTasks = listOf(
            Task("Sample3", "Description3", currentTime, listOf("test")),
            Task("Sample4", "Description4", currentTime, listOf("collection of tips", "tip"))
    )

    val taskDBGatewayMock by memoized(CachingMode.GROUP) {
        mock<TaskDBGateway> {
            on { fetchTasks() } doReturn tasks
        }
    }

    val useCase: FindTaskByTagUseCase by memoized(CachingMode.GROUP) { FindTaskByTagUseCaseImpl(taskDBGatewayMock) }
    Feature("FindTaskByTagUseCase") {
        Scenario("use empty tag list") {
            lateinit var result: List<Task>
            When("find tasks with empty tag list") {
                result = useCase.find(emptyList())
            }

            Then("result should be empty list") {
                result shouldBe emptyList()
            }
        }

        Scenario("use not empty tag list") {

            lateinit var result: List<Task>
            When("find tasks with empty tag list") {
                result = useCase.find(tags)
            }

            Then("result should be expected list") {
                assertEquals(result, expectedTasks)
            }
        }
    }
})