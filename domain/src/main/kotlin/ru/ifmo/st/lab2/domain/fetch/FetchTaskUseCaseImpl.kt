package ru.ifmo.st.lab2.domain.fetch

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.FetchTaskUseCase
import ru.ifmo.st.lab2.gateway.TaskDBGateway

class FetchTaskUseCaseImpl(private val taskDBGateway: TaskDBGateway) : FetchTaskUseCase {
    override operator fun invoke(): List<Task> {
        return taskDBGateway.fetchTasks()
    }
}
