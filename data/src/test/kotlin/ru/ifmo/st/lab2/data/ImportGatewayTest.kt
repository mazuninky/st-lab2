package ru.ifmo.st.lab2.data

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.data.jdbc.DB
import ru.ifmo.st.lab2.data.jdbc.JDBCTaskDBGateway
import ru.ifmo.st.lab2.data.serialization.SerializationExportGateway
import ru.ifmo.st.lab2.data.serialization.SerializationImportGateway
import ru.ifmo.st.lab2.gateway.ExportGateway
import ru.ifmo.st.lab2.gateway.IOGateway
import ru.ifmo.st.lab2.gateway.ImportGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import ru.ifmo.st.lab2.sample.makeFixedTask
import java.io.File

class ImportGatewayTest {
    companion object {
        lateinit var importGateway: ImportGateway

        @BeforeAll
        @JvmStatic
        fun init() {
            importGateway = SerializationImportGateway()
        }
    }

    private val expected = listOf(
        makeFixedTask(1),
        makeFixedTask(2),
        makeFixedTask(3),
        makeFixedTask(4),
        makeFixedTask(5)
    )

    private val data = """
        [{"id":1,"name":"Sample task1","description":"Simple description1","dueData":"01/01/1970 03:00:00.000","state":"Backlog","tags":["sample","tags"]},{"id":2,"name":"Sample task2","description":"Simple description2","dueData":"01/01/1970 03:00:00.000","state":"Backlog","tags":["sample","tags"]},{"id":3,"name":"Sample task3","description":"Simple description3","dueData":"01/01/1970 03:00:00.000","state":"Backlog","tags":["sample","tags"]},{"id":4,"name":"Sample task4","description":"Simple description4","dueData":"01/01/1970 03:00:00.000","state":"Backlog","tags":["sample","tags"]},{"id":5,"name":"Sample task5","description":"Simple description5","dueData":"01/01/1970 03:00:00.000","state":"Backlog","tags":["sample","tags"]}]
    """.trimIndent()

    @Test
    fun `text export`() {
        Assertions.assertEquals(expected, importGateway.import(data))
    }
}
