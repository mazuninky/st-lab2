package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.ExportDBUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram

class ExportProgram(private val export: ExportDBUseCase) : ArgumentCommandProgram() {
    override fun validateArgs(args: List<String>) = args.size == 1

    override fun afterStart() {
        if (args.first().isBlank()) {
            showMessage("Имя файла не может быть пустым!")
        } else {
            export(args.first())
        }
    }
}
