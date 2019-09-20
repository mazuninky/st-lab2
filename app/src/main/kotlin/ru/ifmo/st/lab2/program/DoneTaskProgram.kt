package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.TaskState
import ru.ifmo.st.lab2.domain.DeleteTaskUseCase
import ru.ifmo.st.lab2.domain.DoneTaskUseCase
import ru.ifmo.st.lab2.domain.FindTaskByIdOrNameUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram

class DoneTaskProgram(private val findTaskByIdOrName: FindTaskByIdOrNameUseCase,
                      private val done: DoneTaskUseCase) : ArgumentCommandProgram() {
    companion object {
        const val ID_ERROR = "Невозможно найти задания по заданному id или имени!"
        const val ALREADY_DONE = "адача уже завершена!"
        const val OK = "Задание успешно завершено"
    }

    override fun validateArgs(args: List<String>) = args.isNotEmpty()

    override fun afterStart() {
        val task = findTaskByIdOrName(args.joinToString(" "))
        if (task == null) {
            showMessage(ID_ERROR)
        } else {
            if (task.state == TaskState.Done) {
                showMessage(ALREADY_DONE)
            } else {
                done(task)
                showMessage(OK)
            }
        }

    }

}
