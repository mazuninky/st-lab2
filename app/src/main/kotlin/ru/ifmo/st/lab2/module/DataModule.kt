package ru.ifmo.st.lab2.module

import ru.ifmo.st.lab2.data.DiskIOGateway
import ru.ifmo.st.lab2.data.jdbc.DB
import ru.ifmo.st.lab2.data.jdbc.JDBCTaskDBGateway
import ru.ifmo.st.lab2.data.ktor.KtorServerGateway
import ru.ifmo.st.lab2.data.serialization.SerializationExportGateway
import ru.ifmo.st.lab2.data.serialization.SerializationImportGateway
import ru.ifmo.st.lab2.gateway.*
import ru.ifmo.st.lab2.sl.buildContainer
import ru.ifmo.st.lab2.sl.get

val dataModule = buildContainer {
    single { DB() }
    single<IOGateway> { DiskIOGateway() }
    single<ExportGateway> { SerializationExportGateway() }
    single<ImportGateway> { SerializationImportGateway() }
    single<TaskDBGateway> { JDBCTaskDBGateway(get()) }
    single<ServerGateway> { KtorServerGateway() }
    single<UserCredentialsGatewy> { MemoryUserCredentialsGatewy() }
}
