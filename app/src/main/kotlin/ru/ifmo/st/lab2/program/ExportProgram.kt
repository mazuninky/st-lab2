package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.ExportDBUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram

class ExportProgram(private val export: ExportDBUseCase) : ArgumentCommandProgram() {
    companion object {
        const val OK = "База данных была успешно импортирована"
        const val FILE_IS_EMPTY = "Имя файла не может быть пустым"
    }

    override fun validateArgs(args: List<String>) = args.size == 1

    override fun afterStart() {
        if (args.first().isBlank()) {
            showMessage(FILE_IS_EMPTY)
            return
        }
        export(args.first())
        showMessage(OK)
    }
}
