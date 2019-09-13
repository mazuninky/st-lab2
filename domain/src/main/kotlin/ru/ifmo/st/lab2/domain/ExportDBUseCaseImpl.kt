package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.gateway.ExportGateway
import ru.ifmo.st.lab2.gateway.IOGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway

class ExportDBUseCaseImpl(
        private val exportGateway: ExportGateway,
        private val ioGateway: IOGateway,
        private val taskDBUGateway: TaskDBGateway
) : ExportDBUseCase {

    override operator fun invoke(fileName: String) {
        val tasks = taskDBUGateway.fetchTasks()
        val data = exportGateway.export(tasks)
        ioGateway.writeToFile(fileName, data)
    }
}
