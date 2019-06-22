package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.shouldBe
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.gherkin.Feature
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object FetchNActualTaskUseCaseSpek : Spek({
    val limit = 2
    val currentTime = Calendar.getInstance().time.time

    val tasks = listOf(
            Task("Sample1", "Description1", Date(currentTime - 5)),
            Task("Sample2", "Description2", Date(currentTime - 3)),
            Task("Sample4", "Description4", Date(currentTime + 5001)),
            Task("Sample3", "Description3", Date(currentTime + 5000)),
            Task("Sample5", "Description5", Date(currentTime + 5003))
    )

    val expectedTasks = listOf(
            Task("Sample3", "Description3", Date(currentTime + 5000)),
            Task("Sample4", "Description4", Date(currentTime + 5001))
    )

    val taskDBGatewayMock by memoized(CachingMode.GROUP) {
        mock<TaskDBGateway> {
            on { fetchTasks() } doReturn tasks
        }
    }

    val useCase: FetchNActualTaskUseCase by memoized(CachingMode.GROUP) { FetchNActualTaskUseCaseImpl(taskDBGatewayMock) }
    Feature("FetchNActualTaskUseCase") {
        Scenario("fetch task with negative limit") {
            When("useCase should be throws IllegalArgumentException") {
                assertFailsWith<IllegalArgumentException> { useCase.fetch(-1) }
            }
        }

        Scenario("use zero limit") {
            lateinit var result: List<Task>
            When("fetch task with zero limit") {
                result = useCase.fetch(0)
            }

            Then("result should be empty list") {
                result shouldBe emptyList()
            }
        }

        Scenario("use $limit limit") {
            lateinit var result: List<Task>
            When("fetch task with $limit limit") {
                result = useCase.fetch(limit)
            }

            Then("useCase should be call gateway method") {
                verify(taskDBGatewayMock).fetchTasks()
            }

            Then("result should be expected list") {
                assertEquals(result, expectedTasks)
            }
        }
    }
})
