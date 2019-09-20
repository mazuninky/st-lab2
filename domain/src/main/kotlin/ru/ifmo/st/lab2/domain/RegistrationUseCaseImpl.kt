package ru.ifmo.st.lab2.domain

import kotlinx.coroutines.runBlocking
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.ServerGateway

class RegistrationUseCaseImpl(private val serverGateway: ServerGateway) : RegistrationUseCase {
    override operator fun invoke(username: String, password: String): Boolean {
        check(username.length > 3)
        check(password.length > 4)
        return runBlocking { serverGateway.registration(username, password) }
    }
}
