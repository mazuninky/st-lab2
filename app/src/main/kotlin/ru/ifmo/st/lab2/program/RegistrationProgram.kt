package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.ImportDBUseCase
import ru.ifmo.st.lab2.domain.ImportStrategy
import ru.ifmo.st.lab2.domain.LoginUseCase
import ru.ifmo.st.lab2.domain.RegistrationUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram

class RegistrationProgram(private val registrate: RegistrationUseCase) : ArgumentCommandProgram() {
    companion object {
        const val OK = "Пользователь зарегистрирован"
        const val REG_ERROR = "Произошла проблема при регистрации"
    }

    override fun validateArgs(args: List<String>) = args.size == 2

    override fun afterStart() {
        if (registrate(args.first(), args.second())) {
            showMessage(OK)
        } else {
            showMessage(REG_ERROR)
        }
    }

}
