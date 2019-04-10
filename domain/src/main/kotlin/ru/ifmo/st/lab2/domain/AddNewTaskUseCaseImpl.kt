package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway

class AddNewTaskUseCaseImpl(private val taskDBGateway: TaskDBGateway) : AddNewTaskUseCase {
    override fun addNewTask(task: Task) {
        taskDBGateway.addTask(task)
    }
}