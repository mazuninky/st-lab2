package ru.ifmo.st.lab2.gateway

import ru.ifmo.st.lab2.core.Credentials
import ru.ifmo.st.lab2.domains.SyncType

interface ServerGateway {
    suspend fun login(login: String, pass: String): Boolean
    suspend fun registration(login: String, pass: String): Boolean
    suspend fun loadToServer(credentials: Credentials, dataBlob: String): Boolean
    suspend fun loadFromServer(credentials: Credentials): String?
}
