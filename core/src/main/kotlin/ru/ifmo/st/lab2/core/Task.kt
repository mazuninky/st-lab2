package ru.ifmo.st.lab2.core

import java.util.*

data class Task(val name: String, val description: String, val dueData: Date, val tags: List<String> = emptyList())