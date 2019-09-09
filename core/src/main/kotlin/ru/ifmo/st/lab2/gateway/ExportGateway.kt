package ru.ifmo.st.lab2.gateway

import ru.ifmo.st.lab2.core.Task

interface ExportGateway {
    fun export(tasks: List<Task>) : String
}
