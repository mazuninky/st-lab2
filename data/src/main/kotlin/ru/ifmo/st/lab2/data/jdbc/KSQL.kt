package ru.ifmo.st.lab2.data.jdbc

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.core.TaskState
import java.sql.ResultSet

fun List<String>.toSQLList() = joinToString(separator = ",") { "'$it'" }

fun TaskState.toId(): Int = when (this) {
    TaskState.Backlog -> 1
    TaskState.InProgress -> 2
    TaskState.Done -> 3
}

fun Int.toTaskState(): TaskState = when (this) {
    1 -> TaskState.Backlog
    2 -> TaskState.InProgress
    3 -> TaskState.Done
    else -> throw IllegalArgumentException()
}

fun ResultSet.fetchTask(): Task {
    val id = getLong("id")
    val name = getString("name")
    val description = getString("description")
    val dueDate = getDate("due_date")
    val state = getInt("state_id")
    val tags = getArray("array_agg").array as Array<String>

    return Task(name, description, dueDate, tags.toList(), state.toTaskState(), id)
}
