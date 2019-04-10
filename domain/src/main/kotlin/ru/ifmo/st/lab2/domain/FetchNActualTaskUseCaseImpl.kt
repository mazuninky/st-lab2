package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.lang.IllegalArgumentException
import java.util.*

class FetchNActualTaskUseCaseImpl(private val taskDBGateway: TaskDBGateway) : FetchNActualTaskUseCase {
    override fun fetch(limit: Int): List<Task> {
        if (limit < 0)
            throw IllegalArgumentException()

        if (limit == 0)
            return emptyList()

        val tasks = taskDBGateway.fetchTasks()
        val today = Calendar.getInstance().time
        return tasks
                .filter {
                    val due = it.dueData
                    val isTrue = it.dueData.after(today)
                    it.dueData.after(today)
                }
                .sortedBy { it.dueData.time - today.time }
                .take(limit)
    }
}