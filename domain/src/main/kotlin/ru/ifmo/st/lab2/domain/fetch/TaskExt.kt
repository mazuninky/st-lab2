package ru.ifmo.st.lab2.domain.fetch

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.core.TaskState
import java.util.*

internal fun Task.isOverdue(): Boolean {
    val today = Calendar.getInstance().time
    return dueData.before(today)
}

internal fun Task.isActual(): Boolean {
    val today = Calendar.getInstance().time
    return dueData.after(today)
}

internal fun Task.isDone(): Boolean {
    return state == TaskState.Done
}

internal fun Task.isNotDone(): Boolean {
    return state != TaskState.Done
}
