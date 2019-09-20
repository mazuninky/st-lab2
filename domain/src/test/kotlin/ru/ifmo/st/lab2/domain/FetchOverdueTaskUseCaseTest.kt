package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.fetch.FetchActualTaskUseCaseImpl
import ru.ifmo.st.lab2.domain.fetch.FetchNActualTaskUseCaseImpl
import ru.ifmo.st.lab2.domain.fetch.FetchOverdueTaskUseCaseImpl
import ru.ifmo.st.lab2.gateway.TaskDBGateway

class FetchOverdueTaskUseCaseTest {
    private lateinit var dbGateway: TaskDBGateway
    private lateinit var fetch: FetchOverdueTaskUseCase

    private val overdueTask1 = makeOutdatedTask(1, 50)
    private val overdueTask2 = makeOutdatedTask(4, 48)

    private val tasks = listOf(
        overdueTask1,
        makeActualTask(2),
        makeOutdatedDoneTask(3),
        overdueTask2,
        makeActualTask(5),
        makeActualDoneTask(6)
    )

    private val expectedTasks = listOf(
        overdueTask1,
        overdueTask2
    )

    @BeforeEach
    fun init() {
        dbGateway = mock {
            on { fetchTasks() } doReturn tasks
        }
        fetch = FetchOverdueTaskUseCaseImpl(dbGateway)
    }

    @Test
    fun `when invoke UseCase`() {
        val actual = fetch()
        verify(dbGateway).fetchTasks()
        Assertions.assertEquals(expectedTasks, actual)
    }
}
