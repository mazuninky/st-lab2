package ru.ifmo.st.lab2.domain

import kotlinx.coroutines.runBlocking
import ru.ifmo.st.lab2.domains.SyncServerUseCase
import ru.ifmo.st.lab2.domains.SyncType
import ru.ifmo.st.lab2.gateway.*

class SyncServerUseCaseImpl(
    private val dbGateway: TaskDBGateway,
    private val serverGateway: ServerGateway,
    private val userCredentialsGatewy: UserCredentialsGatewy,
    private val exportGateway: ExportGateway,
    private val importGateway: ImportGateway
) : SyncServerUseCase {
    override operator fun invoke(syncType: SyncType): Boolean {
        val creds = userCredentialsGatewy.fetch()
        checkNotNull(creds)

        val tasks = dbGateway.fetchTasks()
        val data = exportGateway.export(tasks)

        return try {
            runBlocking {
                when (syncType) {
                    SyncType.Download -> {
                        val fetched = serverGateway.loadFromServer(creds)
                        if (fetched != null) {
                            val imported = importGateway.import(fetched)
//                            dbGateway.clear()
                            dbGateway.addAll(imported)
                            true
                        } else false
                    }
                    SyncType.Upload -> {
                        serverGateway.loadToServer(creds, data)
                    }
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
