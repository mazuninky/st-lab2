package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway

class DeleteTaskUseCaseImpl(private val DBGateway: TaskDBGateway) : DeleteTaskUseCase {
    override fun invoke(task: Task) {
        DBGateway.deleteTask(task)
    }
}
