package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.ImportDBUseCase
import ru.ifmo.st.lab2.domain.ImportStrategy
import ru.ifmo.st.lab2.domain.LoginUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram

class UserProgram(private val user: FetchUserInfoUseCase) : ArgumentCommandProgram() {
    companion object {
        const val EMPTY = "Пользователь отсуствует"
        const val OK = "Username пользователя: "
    }

    override fun validateArgs(args: List<String>) = args.isEmpty()

    override fun afterStart() {
        val username = user()
        if (username != null) {
            showMessage(OK)
            showMessage(username)
        } else {
            showMessage(EMPTY)
        }
    }

}
