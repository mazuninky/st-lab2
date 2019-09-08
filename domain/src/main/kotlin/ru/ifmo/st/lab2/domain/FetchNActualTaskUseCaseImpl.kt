package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.lang.IllegalArgumentException
import java.util.*

class FetchNActualTaskUseCaseImpl(private val useCase: FetchActualTaskUseCase) : FetchNActualTaskUseCase {
    override fun fetch(limit: Int): List<Task> {
        check(limit > 0)

        if (limit == 0)
            return emptyList()

        return useCase.fetch().take(limit)
    }
}
