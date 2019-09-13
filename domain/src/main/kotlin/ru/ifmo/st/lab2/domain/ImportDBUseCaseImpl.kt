package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.domain.ImportStrategy.*
import ru.ifmo.st.lab2.gateway.ExportGateway
import ru.ifmo.st.lab2.gateway.IOGateway
import ru.ifmo.st.lab2.gateway.ImportGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.io.File

class ImportDBUseCaseImpl(
        private val importGateway: ImportGateway,
        private val ioGateway: IOGateway,
        private val taskDBUGateway: TaskDBGateway
) : ImportDBUseCase {
    override fun invoke(fileName: String, strategy: ImportStrategy) {
        val importFile = File(fileName)
        check(importFile.exists())

        val importTasks = importGateway.import(importFile.readText())
        when (strategy) {
            Add -> {
                taskDBUGateway.addAll(importTasks.map { it.id = null; it })
            }
            AcceptOwn -> {

            }
            AcceptTheir -> {

            }
        }
    }
    //    override operator fun invoke(fileName: String) {
//        val tasks = taskDBUGateway.fetchTasks()
//        val data = exportGateway.export(tasks)
//        ioGateway.writeToFile(fileName, data)
//    }
}
