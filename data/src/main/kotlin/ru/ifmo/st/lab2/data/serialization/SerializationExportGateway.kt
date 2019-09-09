package ru.ifmo.st.lab2.data.serialization

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.ExportGateway

class SerializationExportGateway : ExportGateway {
    override fun export(tasks: List<Task>): String {
        val json = Json(JsonConfiguration.Stable)
        return json.stringify(TaskDTO.serializer().list, tasks.map(Task::toDTO))
    }
}
