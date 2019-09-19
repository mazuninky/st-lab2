package ru.ifmo.st.lab2.data.serialization

import kotlinx.serialization.*
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.core.TaskState
import java.util.*

@Serializable
data class TaskDTO(
        val id: Long,
        val name: String,
        val description: String,
        @Serializable(with = DateSerializer::class) val dueData: Date,
        val state: TaskState,
        val tags: List<String> = emptyList()
)

fun Task.toDTO(): TaskDTO {
    val id = checkNotNull(id)
    return TaskDTO(id, name, description, dueData, state, tags)
}

fun TaskDTO.toEntity() = Task(
        name, description, dueData, tags, state, id
)
//        TaskDTO(val name: String,
//                val description: String,
//                val dueData: Date,
//                val tags: List<String> = emptyList(),
//                val id: Long? = null)
