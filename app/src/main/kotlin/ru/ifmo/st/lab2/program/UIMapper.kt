package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.Task
import java.lang.StringBuilder

fun Task.toView(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("Название: '${name}', ")
    if (description.isNotEmpty())
        stringBuilder.append("Описание: '${description}', ")
    stringBuilder.append("Дедлайн: '${dueData}'")
    if (tags.isNotEmpty()) {
        stringBuilder.append(", Теги: ")
        stringBuilder.append(tags.joinToString(separator = ", "))
    }
    return stringBuilder.toString()
}
