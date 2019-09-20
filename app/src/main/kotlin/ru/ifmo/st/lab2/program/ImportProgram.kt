package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.ImportDBUseCase
import ru.ifmo.st.lab2.domain.ImportStrategy
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram

class ImportProgram(private val import: ImportDBUseCase) : ArgumentCommandProgram() {
    companion object {
        const val ADD_STRATEGY = "add"
        const val ACCEPT_OWN_STRATEGY = "own"
        const val ACCEPT_THEIR_STRATEGY = "their"
        const val FILE_IS_EMPTY = "Имя файла не может быть пустым"
        const val STRATEGY_ERROR = "Неверное название стратегии"
        const val OK = "Задачи были импортированы"
    }

    override fun validateArgs(args: List<String>) = args.size == 1 || args.size == 2

    override fun afterStart() {
        if (args.first().isBlank()) {
            showMessage(FILE_IS_EMPTY)
        } else {
            val strategy = when (args.getOrElse(1) { ADD_STRATEGY }) {
                ADD_STRATEGY -> ImportStrategy.Add
                ACCEPT_OWN_STRATEGY -> ImportStrategy.Add
                ACCEPT_THEIR_STRATEGY -> ImportStrategy.Add
                else -> null
            }

            if (strategy == null) {
                showMessage(STRATEGY_ERROR)
                return
            }

            import(args.first(), strategy)
            showMessage(OK)
        }
    }

}
