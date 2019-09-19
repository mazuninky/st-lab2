package ru.ifmo.st.lab2.domain.fetch

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.FetchOverdueTaskUseCase
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.util.*

class FetchOverdueTaskUseCaseImpl(private val dbGateway: TaskDBGateway) : FetchOverdueTaskUseCase {
    override fun invoke(): List<Task> {
        val tasks = dbGateway.fetchTasks()
        val today = Calendar.getInstance().time
        return tasks
                .filter(Task::isOverdue)
                .filter(Task::isNotDone)
                .sortedBy { it.dueData.time - today.time }
    }
}
