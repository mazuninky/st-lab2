package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import kotlin.test.assertFailsWith

class DeleteTaskUseCaseTest {
    private lateinit var dbGateway: TaskDBGateway
    private lateinit var delete: DeleteTaskUseCase

    @BeforeEach
    fun init() {
        dbGateway = mock {}
        delete = DeleteTaskUseCaseImpl(dbGateway)
    }

    @Test
    fun `when invoke UseCase`() {
        delete(sampleTask)
        verify(dbGateway).deleteTask(sampleTask)
    }

    @Test
    fun `when invoke UseCase with null id`() {
        assertFailsWith<IllegalStateException> {
            delete(makeSampleTask())
        }
    }
}
