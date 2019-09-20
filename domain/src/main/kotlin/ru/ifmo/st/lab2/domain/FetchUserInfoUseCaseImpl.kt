package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task

class FetchUserInfoUseCaseImpl(private val userCredentialsGatewy: UserCredentialsGatewy) : FetchUserInfoUseCase {
    override operator fun invoke(): String? {
        return userCredentialsGatewy.fetch()?.username
    }
}
