package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway

class FindTaskByTagUseCaseImpl(private val taskDBGateway: TaskDBGateway) : FindTaskByTagUseCase {
    override operator fun invoke(tags: List<String>): List<Task> {
        if (tags.isEmpty())
            return emptyList()

        return taskDBGateway.fetchTasks()
                .filter { task ->
                    task.tags.any { tagString ->
                        tags.any { tag -> tagString.contains(tag) }
                    }
                }
    }
}
