package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.gateway.ExportGateway
import ru.ifmo.st.lab2.gateway.IOGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import kotlin.test.assertFailsWith
import ru.ifmo.st.lab2.sample.makeSampleTask
import ru.ifmo.st.lab2.sample.sampleTask
import ru.ifmo.st.lab2.sample.*

class ExportDBUseCaseTest {
    private lateinit var exportGateway: ExportGateway
    private lateinit var ioGateway: IOGateway
    private lateinit var taskDBUGateway: TaskDBGateway
    private lateinit var export: ExportDBUseCase

    private val tasks = listOf(
        sampleTask,
        makeActualTask(),
        makeActualDoneTask(),
        makeOutdatedTask()
    )

    private val fileName = "export.json"

    private val data = "exported db"

    @BeforeEach
    fun init() {
        exportGateway = mock {
            on { export(tasks) } doReturn data
        }
        ioGateway = mock {
        }
        taskDBUGateway = mock {
            on { fetchTasks() } doReturn tasks
        }
        export = ExportDBUseCaseImpl(exportGateway, ioGateway, taskDBUGateway)
    }


    @Test
    fun `when invoke UseCase with valid file name`() {
        export(fileName)

        verify(taskDBUGateway).fetchTasks()
        verify(ioGateway).writeToFile(fileName, data)
        verify(exportGateway).export(tasks)
    }

    @Test
    fun `when invoke UseCase with blank or empty file name`() {
        assertFailsWith<IllegalStateException> {
            export("    ")
        }
    }
}
