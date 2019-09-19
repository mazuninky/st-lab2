package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.ImportDBUseCase
import ru.ifmo.st.lab2.domain.ImportStrategy
import ru.ifmo.st.lab2.program.main.CommandProgram

class ImportProgram(private val import: ImportDBUseCase) : CommandProgram() {
    companion object {
        const val ADD_STRATEGY = "add"
        const val ACCEPT_OWN_STRATEGY = "own"
        const val ACCEPT_THEIR_STRATEGY = "their"
    }

    override fun validateArgs(args: List<String>) = args.size == 1 || args.size == 2

    override fun afterStart() {
        if (args.first().isBlank()) {
            showMessage("Имя файла не может быть пустым!")
        } else {
            val strategy = when (args.getOrElse(1) { ADD_STRATEGY }) {
                ADD_STRATEGY -> ImportStrategy.Add
                ACCEPT_OWN_STRATEGY -> ImportStrategy.Add
                ACCEPT_THEIR_STRATEGY -> ImportStrategy.Add
                else -> null
            }

            if (strategy == null) {
                showMessage("Неверное название стратегии")
                return
            }

            import(args.first(), strategy)
        }
        finish()
    }

    override suspend fun process(input: String) {

    }
}
