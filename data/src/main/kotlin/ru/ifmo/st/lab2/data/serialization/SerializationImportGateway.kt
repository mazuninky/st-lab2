package ru.ifmo.st.lab2.data.serialization

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.ImportGateway
import java.io.File

class SerializationImportGateway : ImportGateway {
    override fun import(text: String): List<Task> {
        val json = Json(JsonConfiguration.Stable)
        return json.parse(TaskDTO.serializer().list, text)
                .map(TaskDTO::toEntity)
    }
}
