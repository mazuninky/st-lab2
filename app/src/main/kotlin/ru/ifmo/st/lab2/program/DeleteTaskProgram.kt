package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.DeleteTaskUseCase
import ru.ifmo.st.lab2.domain.FindTaskByIdOrNameUseCase
import ru.ifmo.st.lab2.domain.ImportDBUseCase
import ru.ifmo.st.lab2.domain.ImportStrategy
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram

class DeleteTaskProgram(
    private val findTaskByIdOrName: FindTaskByIdOrNameUseCase,
    private val deleteTask: DeleteTaskUseCase
) : ArgumentCommandProgram() {

    companion object {
        const val ID_ERROR = "Невозможно найти задания по заданному id или имени!"
        const val OK = "Успешно удалено"
    }

    override fun validateArgs(args: List<String>) = args.isNotEmpty()

    override fun afterStart() {
        val task = findTaskByIdOrName(args.joinToString(" "))
        if (task == null) {
            showMessage(ID_ERROR)
        } else {
            deleteTask(task)
            showMessage(OK)
        }
    }
}
