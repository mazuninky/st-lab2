package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.core.TaskState
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import kotlin.test.assertFailsWith

class DoneTaskUseCaseTest {
    private lateinit var dbGateway: TaskDBGateway
    private lateinit var done: DoneTaskUseCase

    @BeforeEach
    fun init() {
        dbGateway = mock {}
        done = DoneTaskUseCaseImpl(dbGateway)
    }

    @Test
    fun `when invoke UseCase`() {
        done(sampleTask)

        verify(dbGateway).update(sampleTask.copy(state = TaskState.Done))
    }
}
