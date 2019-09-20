package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.ServerGateway

class RegistrationUseCase(private val serverGateway: ServerGateway) : RegistrationUseCaseImpl {
    override operator fun invoke(username: String, password: String): Boolean {
        return runBlocking { serverGateway.registration(username, password) }
    }
}
