package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.ImportDBUseCase
import ru.ifmo.st.lab2.domain.ImportStrategy
import ru.ifmo.st.lab2.domain.LoginUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram

class RegistrationProgram(private val registrate: RegistrationUseCase) : ArgumentCommandProgram() {
    companion object {
        const val OK = "Пользователь зарегистрирован"
        const val INVALID_CREDITS = "Пароль или логин в неверном формате"
    }

    override fun validateArgs(args: List<String>) = args.size == 2

    override fun afterStart() {
        if (registrate(args.first(), args.second())) {
            showMessage(OK)
        } else {
            showMessage(INVALID_CREDITS)
        }
    }

}
