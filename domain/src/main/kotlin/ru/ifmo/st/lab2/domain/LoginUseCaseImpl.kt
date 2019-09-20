package ru.ifmo.st.lab2.domain

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import ru.ifmo.st.lab2.core.Credentials
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.ServerGateway
import ru.ifmo.st.lab2.gateway.UserCredentialsGatewy

class LoginUseCaseImpl(
    private val serverGateway: ServerGateway,
    private val userCredentialsGatewy: UserCredentialsGatewy
) : LoginUseCase {
    override fun invoke(username: String, password: String): Boolean {
        check(username.length > 3)
        check(password.length > 4)
        val isOk = runBlocking {
            serverGateway.login(username, password)
        }

        if (isOk)
            userCredentialsGatewy.store(Credentials(username, password))

        return isOk
    }
}
