package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.TaskState
import ru.ifmo.st.lab2.domain.DeleteTaskUseCase
import ru.ifmo.st.lab2.domain.FindTaskByIdOrNameUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram

class DoneTaskProgram(private val findTaskByIdOrName: FindTaskByIdOrNameUseCase,
                      private val deleteTask: DeleteTaskUseCase) : ArgumentCommandProgram() {

    override fun validateArgs(args: List<String>) = args.isEmpty()

    override fun afterStart() {
        val task = findTaskByIdOrName(args.joinToString(" "))
        if (task == null) {
            showMessage("Неверный id или имя")
        } else {
            if (task.state == TaskState.Done) {
                showMessage("Задача уже завершена!")
            } else
                deleteTask(task)
        }

        finish()
    }

}
