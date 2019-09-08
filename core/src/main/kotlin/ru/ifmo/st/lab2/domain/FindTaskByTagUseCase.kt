package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task

interface FindTaskByTagUseCase {
    fun find(tags: List<String>): List<Task>
}
