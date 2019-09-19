package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.lang.IllegalArgumentException
import java.util.*

class FetchTagsUseCaseImpl(private val DBGateway: TaskDBGateway) : FetchTagsUseCase {
    override operator fun invoke(): List<String> {
        return DBGateway.fetchTags()
    }
}
