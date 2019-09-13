package ru.ifmo.st.lab2.core

import java.util.*

data class Task(var name: String,
                var description: String,
                var dueData: Date,
                var tags: List<String> = emptyList(),
                var id: Long? = null)
