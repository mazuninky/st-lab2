package ru.ifmo.st.lab2.gateway

import ru.ifmo.st.lab2.core.Task

interface TaskDBGateway {
    fun addTask(task: Task)

    fun addAll(tasks: List<Task>) {
        tasks.forEach { addTask(it) }
    }

    fun update(task: Task)

    fun containsTask(id: Long): Boolean

    fun fetchTasks(): List<Task>

    fun clear()
}
