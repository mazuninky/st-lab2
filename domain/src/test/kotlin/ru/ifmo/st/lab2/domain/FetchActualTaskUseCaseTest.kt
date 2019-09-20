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
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import ru.ifmo.st.lab2.sample.*

class FetchActualTaskUseCaseTest {
    private lateinit var dbGateway: TaskDBGateway
    private lateinit var fetch: FetchActualTaskUseCase

    private val actualTask1 = makeActualTask(1, 50)
    private val actualTask2 = makeActualTask(4, 48)

    private val tasks = listOf(
        actualTask1,
        makeOutdatedTask(2),
        makeOutdatedTask(3),
        actualTask2,
        makeOutdatedTask(5),
        makeActualDoneTask(6)
    )

    private val expectedTasks = listOf(
        actualTask2,
        actualTask1
    )

    @BeforeEach
    fun init() {
        dbGateway = mock {
            on { fetchTasks() } doReturn tasks
        }
        fetch = FetchActualTaskUseCaseImpl(dbGateway)
    }

    @Test
    fun `when invoke UseCase`() {
        val actual = fetch()
        verify(dbGateway).fetchTasks()
        Assertions.assertEquals(expectedTasks, actual)
    }
}
