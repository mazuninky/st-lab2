package ru.ifmo.st.lab2.gateway

import ru.ifmo.st.lab2.core.Credentials
import ru.ifmo.st.lab2.domains.SyncType

interface ServerGateway {
    suspend fun login(login: String, pass: String): Boolean
    suspend fun registration(login: String, pass: String): Boolean
    suspend fun sync(credentials: Credentials, dataBlob: String, syncType: SyncType)
}
