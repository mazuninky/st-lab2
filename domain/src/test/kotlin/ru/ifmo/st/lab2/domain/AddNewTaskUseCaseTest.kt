package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import ru.ifmo.st.lab2.sample.makeSampleTask
import ru.ifmo.st.lab2.sample.sampleTask

class AddNewTaskUseCaseTest {
    private lateinit var dbGateway: TaskDBGateway
    private lateinit var addNewTask: AddNewTaskUseCase

    @BeforeEach
    fun init() {
        dbGateway = mock {}
        addNewTask = AddNewTaskUseCaseImpl(dbGateway)
    }

    @Test
    fun `when invoke UseCase`() {
        addNewTask(sampleTask)
        verify(dbGateway).addTask(sampleTask)
    }
}
