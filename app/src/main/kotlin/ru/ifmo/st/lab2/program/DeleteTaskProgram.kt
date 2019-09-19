package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.DeleteTaskUseCase
import ru.ifmo.st.lab2.domain.FindTaskByIdOrNameUseCase
import ru.ifmo.st.lab2.domain.ImportDBUseCase
import ru.ifmo.st.lab2.domain.ImportStrategy
import ru.ifmo.st.lab2.program.main.CommandProgram

class DeleteTaskProgram(private val findTaskByIdOrName: FindTaskByIdOrNameUseCase,
                        private val deleteTask: DeleteTaskUseCase) : CommandProgram() {

    override fun validateArgs(args: List<String>) = args.isEmpty()

    override fun afterStart() {
        val task = findTaskByIdOrName(args.joinToString(" "))
        if (task == null) {
            showMessage("Неверный id или имя")
        } else {
            deleteTask(task)
        }

        finish()
    }

    override suspend fun process(input: String) {

    }
}
