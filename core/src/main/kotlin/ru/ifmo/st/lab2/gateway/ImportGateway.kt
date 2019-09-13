package ru.ifmo.st.lab2.gateway

import ru.ifmo.st.lab2.core.Task

interface ImportGateway {
    fun import(text: String): List<Task>
}
