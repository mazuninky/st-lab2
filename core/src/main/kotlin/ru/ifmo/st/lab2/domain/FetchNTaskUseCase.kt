package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task

interface FetchNTaskUseCase {
    operator fun invoke(limit: Int): List<Task>
}
