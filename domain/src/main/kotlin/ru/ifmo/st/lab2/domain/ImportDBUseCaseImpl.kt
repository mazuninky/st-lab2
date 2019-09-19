package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.ImportStrategy.*
import ru.ifmo.st.lab2.gateway.ExportGateway
import ru.ifmo.st.lab2.gateway.IOGateway
import ru.ifmo.st.lab2.gateway.ImportGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.io.File

private fun Task.clearId(): Task {
    id = null
    return this
}

class ImportDBUseCaseImpl(
        private val importGateway: ImportGateway,
        private val taskDBUGateway: TaskDBGateway
) : ImportDBUseCase {
    override fun invoke(fileName: String, strategy: ImportStrategy) {
        val importFile = File(fileName)
        check(importFile.exists())

        val importTasks = importGateway.import(importFile.readText())
        when (strategy) {
            Add -> {
                taskDBUGateway.addAll(
                        importTasks
                                .map(Task::clearId)
                )
            }
            AcceptOwn -> {
                taskDBUGateway.addAll(
                        importTasks
                                .filter { val id = checkNotNull(it.id); taskDBUGateway.containsTask(id) }
                                .map(Task::clearId)
                )
            }
            AcceptTheir -> {
                importTasks
                        .filter { val id = checkNotNull(it.id); !taskDBUGateway.containsTask(id) }
                        .forEach { taskDBUGateway.update(it) }
            }
        }
    }
}
