package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task

interface FindTaskByTagUseCase {
    operator fun invoke(tags: List<String>): List<Task>
}
