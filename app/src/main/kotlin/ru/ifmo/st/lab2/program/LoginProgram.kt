package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.ImportDBUseCase
import ru.ifmo.st.lab2.domain.ImportStrategy
import ru.ifmo.st.lab2.domain.LoginUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram

class LoginProgram(private val login: LoginUseCase) : ArgumentCommandProgram() {
    companion object {
        const val OK = "Успешный вход"
        const val INVALID_CREDITS = "Неверный пароль или логин"
        const val PASSWORD_SHORT = "Слишком короткий пароль"
        const val USERNAME_SHORT = "Слишком короткий пароль"
    }

    override fun validateArgs(args: List<String>) = args.size == 2

    override fun afterStart() {
        val username = args.first()
        if (username.length < 3) {
            showMessage(USERNAME_SHORT)
            return
        }
        val password = args.second()
        if (password.length < 4) {
            showMessage(PASSWORD_SHORT)
            return
        }
        if (login(username, password)) {
            showMessage(OK)
        } else {
            showMessage(INVALID_CREDITS)
        }
    }

}
