package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.fetch.FetchNActualTaskUseCaseImpl
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import ru.ifmo.st.lab2.sample.*

class FetchNActualTaskUseCaseTest {
    private lateinit var fetchActual: FetchActualTaskUseCase
    private lateinit var fetchNActual: FetchNActualTaskUseCase

    private val limit = 2

    private val tasks = listOf(
        makeActualTask(1),
        makeActualTask(2),
        makeActualTask(3),
        makeActualTask(4),
        makeActualTask(5)
    )

    private val expectedTasks = tasks.take(limit)

    @BeforeEach
    fun init() {
        fetchActual = mock {
            on { invoke() } doReturn tasks
        }
        fetchNActual = FetchNActualTaskUseCaseImpl(fetchActual)
    }

    @Test
    fun `when invoke UseCase with valid limit`() {
        val actual = fetchNActual(limit)
        verify(fetchActual).invoke()
        Assertions.assertEquals(expectedTasks, actual)
    }

    @Test
    fun `when invoke UseCase with zero limit result should be empty`() {
        val result = fetchNActual(0)
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun `when invoke UseCase with negative limit`() {
        assertFailsWith<IllegalStateException> {
            fetchNActual(-1)
        }
    }
}
