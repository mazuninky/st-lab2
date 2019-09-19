package ru.ifmo.st.lab2.domain

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.ServerGateway

class LoginUseCaseImpl(private val serverGateway: ServerGateway) : LoginUseCase {
    override fun invoke(username: String, password: String): Boolean {
        return runBlocking {
            serverGateway.login(username, password)
        }
    }
}