package ru.ifmo.st.lab2.gateway

interface UserCredentialsGatewy {
    fun store(credentials: Credentials)

    fun fetch(): Credentials?
}
