package ru.ifmo.st.lab2.module

import ru.ifmo.st.lab2.data.DiskIOGateway
import ru.ifmo.st.lab2.data.jdbc.DB
import ru.ifmo.st.lab2.data.jdbc.JDBCTaskDBGateway
import ru.ifmo.st.lab2.data.serialization.SerializationExportGateway
import ru.ifmo.st.lab2.gateway.ExportGateway
import ru.ifmo.st.lab2.gateway.IOGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import ru.ifmo.st.lab2.sl.buildContainer
import ru.ifmo.st.lab2.sl.get

val dataModule = buildContainer {
    single { DB() }
    single<IOGateway> { DiskIOGateway() }
    single<ExportGateway> { SerializationExportGateway() }
    single<TaskDBGateway> { JDBCTaskDBGateway(get()) }
}
