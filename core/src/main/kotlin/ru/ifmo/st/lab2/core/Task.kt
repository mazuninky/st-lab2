package ru.ifmo.st.lab2.core

import java.util.*

enum class TaskState {
    Backlog, InProgress, Done
}

data class Task(var name: String,
                var description: String,
                var dueData: Date,
                var tags: List<String> = emptyList(),
                var state: TaskState = TaskState.Backlog,
                var id: Long? = null)
