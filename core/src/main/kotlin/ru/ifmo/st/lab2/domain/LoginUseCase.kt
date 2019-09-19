package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task

interface LoginUseCase {
     operator fun invoke(username: String, password: String): Boolean
}
