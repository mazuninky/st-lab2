package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.core.Credentials
import ru.ifmo.st.lab2.domains.SyncServerUseCase
import ru.ifmo.st.lab2.domains.SyncType
import ru.ifmo.st.lab2.gateway.ExportGateway
import ru.ifmo.st.lab2.gateway.ServerGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import ru.ifmo.st.lab2.gateway.UserCredentialsGatewy
import kotlin.test.assertFailsWith
import ru.ifmo.st.lab2.sample.*

class SyncServerUseCaseTest {
    private lateinit var sync: SyncServerUseCase

    private lateinit var dbGateway: TaskDBGateway
    private lateinit var serverGateway: ServerGateway
    private lateinit var userCredentialsGatewy: UserCredentialsGatewy
    private lateinit var exportGateway: ExportGateway

    private val credentials = Credentials("username", "password")

    private val data = "data"

    private val tasks = listOf(
        makeFixedTask(1),
        makeFixedTask(2),
        makeFixedTask(3),
        makeFixedTask(4)
    )

    @BeforeEach
    fun init() {
        serverGateway = mock {
            //            onBlocking { this.sync(credentials, data, any()) }
        }
        userCredentialsGatewy = mock {
            on { fetch() } doReturn credentials
        }
        exportGateway = mock {
            on { export(tasks) } doReturn data
        }
        dbGateway = mock {
            on { fetchTasks() } doReturn tasks
        }
        sync = SyncServerUseCaseImpl(dbGateway, serverGateway, userCredentialsGatewy, exportGateway)
    }

    @Test
    fun `when sync with Download`() {
        sync(SyncType.Download)
        runBlocking { verify(serverGateway).sync(credentials, data, SyncType.Download) }
        verify(userCredentialsGatewy).fetch()
        verify(exportGateway).export(tasks)
        verify(dbGateway).fetchTasks()
    }

    @Test
    fun `when sync with Upload`() {
        sync(SyncType.Upload)
        runBlocking { verify(serverGateway).sync(credentials, data, SyncType.Upload) }
        verify(userCredentialsGatewy).fetch()
        verify(exportGateway).export(tasks)
        verify(dbGateway).fetchTasks()
    }

    @Test
    fun `when sync with invalid cred`() {
        userCredentialsGatewy = mock {
            on { fetch() } doReturn null
        }
        sync = SyncServerUseCaseImpl(dbGateway, serverGateway, userCredentialsGatewy, exportGateway)
        assertFailsWith<IllegalStateException> {
            sync(SyncType.Upload)
        }
    }


}
