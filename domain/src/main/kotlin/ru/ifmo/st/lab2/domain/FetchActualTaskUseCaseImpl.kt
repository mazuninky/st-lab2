package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.lang.IllegalArgumentException
import java.util.*

class FetchActualTaskUseCaseImpl(private val taskDBGateway: TaskDBGateway) : FetchActualTaskUseCase {
    override operator fun invoke(): List<Task> {
        val tasks = taskDBGateway.fetchTasks()
        val today = Calendar.getInstance().time
        return tasks
                .filter {
                    it.dueData.after(today)
                }
                .sortedBy { it.dueData.time - today.time }
    }
}
