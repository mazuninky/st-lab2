package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import kotlin.test.assertFailsWith

class FindTaskByIdOrNameUseCaseTest {
    private lateinit var dbGateway: TaskDBGateway
    private lateinit var find: FindTaskByIdOrNameUseCase

    private val id = "12345"

    @BeforeEach
    fun init() {
        dbGateway = mock {
            on { findTaskByIdOrName(id) } doReturn sampleTask
        }
        find = FindTaskByIdOrNameUseCaseImpl(dbGateway)
    }

    @Test
    fun `when invoke UseCase`() {
        val actual = find(id)
        verify(dbGateway).findTaskByIdOrName(id)
        Assertions.assertEquals(sampleTask, actual)
    }

    @Test
    fun `when invoke UseCase with blank id`() {
        assertFailsWith<IllegalStateException> {
            find("    ")
        }
    }
}
