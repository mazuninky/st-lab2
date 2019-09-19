package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task

interface FetchTagsUseCase {
    operator fun invoke(): List<String>
}
