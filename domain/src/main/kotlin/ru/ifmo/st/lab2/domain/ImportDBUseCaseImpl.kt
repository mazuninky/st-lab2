package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.ImportStrategy.*
import ru.ifmo.st.lab2.gateway.ExportGateway
import ru.ifmo.st.lab2.gateway.IOGateway
import ru.ifmo.st.lab2.gateway.ImportGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import java.io.File

fun Task.clearId(): Task {
    id = null
    return this
}

class ImportDBUseCaseImpl(
    private val importGateway: ImportGateway,
    private val ioGateway: IOGateway,
    private val taskDBUGateway: TaskDBGateway
) : ImportDBUseCase {
    override fun invoke(fileName: String, strategy: ImportStrategy) {
        check(fileName.isNotBlank())
        val data = ioGateway.readFromFile(fileName)

        val importTasks = importGateway.import(data)
        when (strategy) {
            Add -> {
                taskDBUGateway.addAll(
                    importTasks
                        .map(Task::clearId)
                )
            }
            AcceptTheir -> {
                taskDBUGateway.addAll(
                    importTasks
                        .filter { val id = checkNotNull(it.id); taskDBUGateway.containsTask(id) }
                        .map(Task::clearId)
                )
            }
            AcceptOwn -> {
                importTasks
                    .filter { val id = checkNotNull(it.id); !taskDBUGateway.containsTask(id) }
                    .forEach { taskDBUGateway.update(it) }
            }
        }
    }
}
