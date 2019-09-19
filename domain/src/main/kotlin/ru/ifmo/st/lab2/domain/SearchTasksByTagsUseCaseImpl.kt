package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway

class SearchTasksByTagsUseCaseImpl(private val dbGateway: TaskDBGateway) : SearchTasksByTagsUseCase {
    override fun invoke(tags: List<String>): List<Task> {
        return dbGateway.findTasksByTags(tags)
    }
}
