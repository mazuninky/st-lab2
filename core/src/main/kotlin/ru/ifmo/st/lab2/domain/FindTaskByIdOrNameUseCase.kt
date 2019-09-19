package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task

interface FindTaskByIdOrNameUseCase {
    operator fun invoke(criterion: String) : Task?
}
