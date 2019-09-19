package ru.ifmo.st.lab2.domain.fetch

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.core.TaskState
import ru.ifmo.st.lab2.domain.FetchActualTaskUseCase
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.util.*

class FetchActualTaskUseCaseImpl(private val dbGateway: TaskDBGateway) : FetchActualTaskUseCase {
    override operator fun invoke(): List<Task> {
        val tasks = dbGateway.fetchTasks()
        val today = Calendar.getInstance().time
        return tasks
                .filter(Task::isActual)
                .filter(Task::isNotDone)
                .sortedBy { it.dueData.time - today.time }
    }
}
