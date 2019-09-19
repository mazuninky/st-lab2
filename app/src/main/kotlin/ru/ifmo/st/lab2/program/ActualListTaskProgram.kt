package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.FetchActualTaskUseCase
import ru.ifmo.st.lab2.domain.FetchNActualTaskUseCase
import ru.ifmo.st.lab2.domain.FetchNTaskUseCase
import ru.ifmo.st.lab2.domain.FetchTaskUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram
import java.lang.NumberFormatException
import java.lang.StringBuilder

class ActualListTaskProgram(private val fetchActualTasks: FetchActualTaskUseCase,
                            private val fetchNActualTasks: FetchNActualTaskUseCase) : ArgumentCommandProgram() {
    companion object {
        const val ErrorMessage = "Ожидалось положительное число"
    }

    override fun validateArgs(args: List<String>) = args.size <= 1

    override fun afterStart() {
        val tasks: List<Task>
        tasks = if (args.isEmpty()) {
            fetchActualTasks()
        } else {
            val n = args.first().toIntOrNull()

            if (n == null) {
                showMessage("Ожидалось число в качестве параметра!")
                return
            }

            if (n < 0) {
                showMessage(ErrorMessage)
                return
            }

            fetchNActualTasks(n)
        }

        tasks.map(Task::toView).forEach(this::showMessage)
    }
}
