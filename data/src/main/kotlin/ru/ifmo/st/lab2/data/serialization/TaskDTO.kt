package ru.ifmo.st.lab2.data.serialization
import kotlinx.serialization.*
import ru.ifmo.st.lab2.core.Task

@Serializable
data class TaskDTO(val name: String)

fun Task.toDTO(): TaskDTO =
        TaskDTO(name)
