package ru.ifmo.st.lab2.gateway

interface ServerGateway {
    suspend fun login(login: String, pass: String): Boolean
}
