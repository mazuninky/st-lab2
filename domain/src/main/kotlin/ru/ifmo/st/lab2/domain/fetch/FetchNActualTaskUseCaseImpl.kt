package ru.ifmo.st.lab2.domain.fetch

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.FetchActualTaskUseCase
import ru.ifmo.st.lab2.domain.FetchNActualTaskUseCase

class FetchNActualTaskUseCaseImpl(private val fetch: FetchActualTaskUseCase) : FetchNActualTaskUseCase {
    override operator fun invoke(limit: Int): List<Task> {
        check(limit > 0)

        if (limit == 0)
            return emptyList()

        return fetch()
                .take(limit)
    }
}
