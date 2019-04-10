package ru.ifmo.st.lab2.gateway

import ru.ifmo.st.lab2.core.Task

interface TaskDBGateway {
    fun addTask(task: Task)
    fun fetchTasks(): List<Task>
}