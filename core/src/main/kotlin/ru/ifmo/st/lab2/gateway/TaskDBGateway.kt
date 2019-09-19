package ru.ifmo.st.lab2.gateway

import ru.ifmo.st.lab2.core.Task

interface TaskDBGateway {
    fun addTask(task: Task)

    fun addAll(tasks: List<Task>) {
        tasks.forEach { addTask(it) }
    }

    fun update(task: Task)

    fun findTaskByIdOrName(value: String): Task?

    fun containsTask(id: Long): Boolean

    fun deleteTask(task: Task)

    fun fetchTasks(): List<Task>

    fun fetchTags(): List<String>

    fun findTasksByTags(tags: List<String>): List<Task>

    fun clear()
}
