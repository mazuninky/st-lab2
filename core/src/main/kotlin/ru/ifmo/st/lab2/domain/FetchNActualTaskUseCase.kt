package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task

interface FetchNActualTaskUseCase {
    operator fun invoke(limit: Int): List<Task>
}
