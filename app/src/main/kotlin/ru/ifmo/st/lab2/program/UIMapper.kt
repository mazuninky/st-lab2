package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.core.TaskState
import java.lang.StringBuilder

fun TaskState.toView(): String = when (this) {
    TaskState.Backlog -> "Не начато"
    TaskState.InProgress -> "В процессе"
    TaskState.Done -> "Завершено"
}

fun Task.toView(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("Название: '${name}', ")
    if (description.isNotEmpty())
        stringBuilder.append("Описание: '${description}', ")
    stringBuilder.append("Дедлайн: '${dueData}', ")
    stringBuilder.append("Состояние: '${state.toView()}'")
    if (tags.isNotEmpty()) {
        stringBuilder.append(", Теги: ")
        stringBuilder.append(tags.joinToString(separator = ", "))
    }
    return stringBuilder.toString()
}
