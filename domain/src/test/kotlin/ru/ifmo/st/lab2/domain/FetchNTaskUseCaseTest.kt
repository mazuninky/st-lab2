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
import ru.ifmo.st.lab2.domain.fetch.FetchNTaskUseCaseImpl
import ru.ifmo.st.lab2.domain.fetch.FetchTaskUseCaseImpl
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import ru.ifmo.st.lab2.sample.*

class FetchNTaskUseCaseTest {
    private lateinit var fetch: FetchTaskUseCase
    private lateinit var fetchN: FetchNTaskUseCase

    private val limit = 2

    private val tasks = listOf(
        makeSampleTask(1),
        makeSampleTask(2),
        makeSampleTask(3),
        makeSampleTask(4)
    )

    private val expectedTasks = tasks.take(limit)

    @BeforeEach
    fun init() {
        fetch = mock {
            on { invoke() } doReturn tasks
        }
        fetchN = FetchNTaskUseCaseImpl(fetch)
    }


    @Test
    fun `when invoke UseCase with valid limit`() {
        val actual = fetchN(limit)
        verify(fetch).invoke()
        Assertions.assertEquals(expectedTasks, actual)
    }

    @Test
    fun `when invoke UseCase with zero limit result should be empty`() {
        val result = fetchN(0)
        Assertions.assertTrue(result.isEmpty())
    }

    @Test
    fun `when invoke UseCase with negative limit`() {
        assertFailsWith<IllegalStateException> {
            fetchN(-1)
        }
    }
}
