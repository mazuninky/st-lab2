package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.FetchNActualTaskUseCase
import ru.ifmo.st.lab2.domain.FetchNTaskUseCase
import ru.ifmo.st.lab2.domain.FetchTaskUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram
import java.lang.NumberFormatException
import java.lang.StringBuilder

class ListTaskProgram(
    private val fetchTasks: FetchTaskUseCase,
    private val fetchNTask: FetchNTaskUseCase
) : ArgumentCommandProgram() {
    companion object {
        const val LimitErrorMessage = "Ожидалось не отрицательное число в качестве параметра"
        const val Empty = "Нет задач!"
        const val List = "Список задач: "
    }

    override fun validateArgs(args: List<String>) = args.size <= 1

    override fun afterStart() {
        val tasks: List<Task>
        tasks = if (args.isEmpty()) {
            fetchTasks()
        } else {
            val n = args.first().toIntOrNull()

            if (n == null || n < 0) {
                showMessage(LimitErrorMessage)
                return
            }

            fetchNTask(n)
        }

        if (tasks.isEmpty()) {
            showMessage(Empty)
        } else {
            showMessage(List)
            tasks.map(Task::toView).forEach(this::showMessage)
        }
    }
}
