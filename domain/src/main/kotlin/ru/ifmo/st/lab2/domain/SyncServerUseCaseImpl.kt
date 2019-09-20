package ru.ifmo.st.lab2.domain

import kotlinx.coroutines.runBlocking
import ru.ifmo.st.lab2.domains.SyncServerUseCase
import ru.ifmo.st.lab2.domains.SyncType
import ru.ifmo.st.lab2.gateway.ExportGateway
import ru.ifmo.st.lab2.gateway.ServerGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import ru.ifmo.st.lab2.gateway.UserCredentialsGatewy

class SyncServerUseCaseImpl(
    private val dbGateway: TaskDBGateway,
    private val serverGateway: ServerGateway,
    private val userCredentialsGatewy: UserCredentialsGatewy,
    private val exportGateway: ExportGateway
) : SyncServerUseCase {
    override operator fun invoke(syncType: SyncType): Boolean {
        val creds = userCredentialsGatewy.fetch()
        checkNotNull(creds)

        val tasks = dbGateway.fetchTasks()
        val data = exportGateway.export(tasks)

        return try {
            runBlocking {
                serverGateway.sync(creds, data, syncType)
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
