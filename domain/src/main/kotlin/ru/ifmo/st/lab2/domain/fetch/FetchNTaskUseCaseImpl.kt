package ru.ifmo.st.lab2.domain.fetch

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.FetchNTaskUseCase
import ru.ifmo.st.lab2.domain.FetchTaskUseCase

class FetchNTaskUseCaseImpl(private val fetch: FetchTaskUseCase) : FetchNTaskUseCase {
    override operator fun invoke(limit: Int): List<Task> {
        check(limit > 0)

        if (limit == 0)
            return emptyList()

        return fetch()
                .take(limit)
    }
}
