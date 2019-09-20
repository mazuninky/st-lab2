package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.gateway.ServerGateway

class SyncServerUseCaseImpl(
    private val dbGateway: DBGateway,
    private val serverGateway: ServerGateway,
    private val userCredentialsGatewy: UserCredentialsGatewy,
    private val exportGateway: ExportGateway
) : SyncServerUseCase() {
    override operator fun invoke(syncType: SyncType): Boolean {
        val creds = userCredentialsGatewy.fetch()
        checkNotNull(creds)

        val tasks = dbGateway.fetchTasks()
        val data = exportGateway.export(tasks)

        runBlocking {
            serverGatewat.sync(creds, datag, syncType)
        }
    }
}
