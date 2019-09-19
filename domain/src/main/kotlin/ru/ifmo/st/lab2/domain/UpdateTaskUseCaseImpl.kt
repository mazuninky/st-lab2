package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway

class UpdateTaskUseCaseImpl(private val dbGateway: TaskDBGateway) : UpdateTaskUseCase {
    override fun invoke(task: Task) {
        dbGateway.update(task)
    }
}
