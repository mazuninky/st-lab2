package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.core.TaskState
import ru.ifmo.st.lab2.gateway.TaskDBGateway

class DoneTaskUseCaseImpl(private val dbGateway: TaskDBGateway) : DoneTaskUseCase {
    override fun invoke(task: Task) {
        dbGateway.update(task.copy(state = TaskState.Done))
    }
}
