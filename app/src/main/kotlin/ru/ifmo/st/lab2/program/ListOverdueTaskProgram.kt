package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.FetchNActualTaskUseCase
import ru.ifmo.st.lab2.domain.FetchNTaskUseCase
import ru.ifmo.st.lab2.domain.FetchOverdueTaskUseCase
import ru.ifmo.st.lab2.domain.FetchTaskUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram
import java.lang.NumberFormatException
import java.lang.StringBuilder

class ListOverdueTaskProgram(private val fetchOverdueTasks: FetchOverdueTaskUseCase) : ArgumentCommandProgram() {
    companion object {
        const val EMPTY = "Нет просроченных заданий!"
        const val HEAD = "Просроченные задания: "
    }

    override fun validateArgs(args: List<String>) = args.isEmpty()

    override fun afterStart() {
        val tasks = fetchOverdueTasks()

        if (tasks.isEmpty()) {
            showMessage(EMPTY)
        } else {
            showMessage(HEAD)
            tasks.map(Task::toView).forEach(this::showMessage)
        }
    }
}
