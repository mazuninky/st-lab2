package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.gateway.ExportGateway
import ru.ifmo.st.lab2.gateway.IOGateway
import ru.ifmo.st.lab2.gateway.ImportGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import kotlin.test.assertFailsWith

class ImportDBUseCaseTest {
    private lateinit var importGateway: ImportGateway
    private lateinit var taskDBUGateway: TaskDBGateway
    private lateinit var ioGateway: IOGateway
    private lateinit var import: ImportDBUseCase

    private val time = actualTime(0)

    private fun makeFixedTask(id: Long) = makeSampleTask(id, date = time)

    private val importedTasks = listOf(
        makeFixedTask(48),
        makeFixedTask(2),
        makeFixedTask(3)
    )

    private val expectedAddStrategyTasks = listOf(
        makeFixedTask(48).apply { id = null },
        makeFixedTask(2).apply { id = null },
        makeFixedTask(3).apply { id = null }
    )

    private val expectedOwnStrategyTasks = listOf(
        makeFixedTask(2)
    )

    private val expectedTheirStrategyTasks = listOf(
        makeFixedTask(48).apply { id = null },
        makeFixedTask(3).apply { id = null }
    )

    private val fileName = "import.json"

    private val data = "import db"

    @BeforeEach
    fun init() {
        ioGateway = mock {
            on { readFromFile(fileName) } doReturn data
        }
        importGateway = mock {
            on { import(data) } doReturn importedTasks
        }
        taskDBUGateway = mock {
            on { containsTask(48) } doReturn true
            on { containsTask(2) } doReturn false
            on { containsTask(3) } doReturn true
        }

        import = ImportDBUseCaseImpl(importGateway, ioGateway, taskDBUGateway)
    }

    @Test
    fun `when invoke UseCase with add strategy`() {
        import(fileName, ImportStrategy.Add)

        verify(taskDBUGateway).addAll(expectedAddStrategyTasks)
        verify(ioGateway).readFromFile(fileName)
        verify(importGateway).import(data)
    }

    @Test
    fun `when invoke UseCase with accept own`() {
        import(fileName, ImportStrategy.AcceptOwn)

        verify(taskDBUGateway).update(expectedOwnStrategyTasks.first())
        verify(ioGateway).readFromFile(fileName)
        verify(importGateway).import(data)
    }

    @Test
    fun `when invoke UseCase with accept their`() {
        import(fileName, ImportStrategy.AcceptTheir)

        verify(taskDBUGateway).addAll(expectedTheirStrategyTasks)
        verify(ioGateway).readFromFile(fileName)
        verify(importGateway).import(data)
    }
//
//    @Test
//    fun `when invoke UseCase with blank or empty file name`() {
//        assertFailsWith<IllegalStateException> {
//            export("    ")
//        }
//        assertFailsWith<IllegalStateException> {
//            export("")
//        }
//    }
}
