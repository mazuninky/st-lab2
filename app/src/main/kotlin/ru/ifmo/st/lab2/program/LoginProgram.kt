package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.ImportDBUseCase
import ru.ifmo.st.lab2.domain.ImportStrategy
import ru.ifmo.st.lab2.domain.LoginUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram

class LoginProgram(private val login: LoginUseCase) : ArgumentCommandProgram() {
    override fun validateArgs(args: List<String>) = args.size == 2

    override fun afterStart() {
        if (login(args.first(), args.second())) {
            showMessage("Успешный вход")
        } else {
            showMessage("Неверный пароль или логин")
        }
    }

}
