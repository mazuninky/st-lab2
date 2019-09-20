package ru.ifmo.st.lab2.gateway

import ru.ifmo.st.lab2.core.Credentials

interface UserCredentialsGatewy {
    fun store(credentials: Credentials)

    fun fetch(): Credentials?
}
