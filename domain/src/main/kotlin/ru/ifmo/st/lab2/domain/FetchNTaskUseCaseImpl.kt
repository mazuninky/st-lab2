package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.util.*

class FetchNTaskUseCaseImpl(private val useCase: FetchTaskUseCase) : FetchNTaskUseCase {
    override operator fun invoke(limit: Int): List<Task> {
        check(limit > 0)

        if (limit == 0)
            return emptyList()

        return useCase()
                .take(limit)
    }
}
