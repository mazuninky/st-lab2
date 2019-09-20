package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.ImportDBUseCase
import ru.ifmo.st.lab2.domain.ImportStrategy
import ru.ifmo.st.lab2.domain.LoginUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram

class LoginProgram(private val login: LoginUseCase) : ArgumentCommandProgram() {
    companion object {
        const val OK = "Успешный вход"
        const val INVALID_CREDITS = "Неверный пароль или логин"
    }

    override fun validateArgs(args: List<String>) = args.size == 2

    override fun afterStart() {
        if (login(args.first(), args.second())) {
            showMessage(OK)
        } else {
            showMessage(INVALID_CREDITS)
        }
    }

}
