package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task

interface FetchTaskUseCase {
    operator fun invoke(): List<Task>
}
