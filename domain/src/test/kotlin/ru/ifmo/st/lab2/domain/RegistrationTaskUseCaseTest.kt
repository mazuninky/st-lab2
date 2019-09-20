package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.gateway.ServerGateway
import ru.ifmo.st.lab2.gateway.TaskDBGateway
import ru.ifmo.st.lab2.gateway.UserCredentialsGatewy
import kotlin.test.assertFailsWith
import ru.ifmo.st.lab2.sample.*

class RegistrationTaskUseCaseTest {
    private lateinit var serverGateway: ServerGateway
    private lateinit var register: RegistrationUseCase

    private val username = "login"
    private val password = "password"

    @BeforeEach
    fun init() {
        serverGateway = mock {
            onBlocking { this.registration(username, password) } doReturn true
        }
        register = RegistrationUseCaseImpl(serverGateway)
    }

    @Test
    fun `when invoke UseCase`() {
        register(username, password)
        runBlocking { verify(serverGateway).registration(username, password) }
    }

    @Test
    fun `when invoke UseCase with invalid username`() {
        assertFailsWith<IllegalStateException> {
            register("123", password)
        }
    }

    @Test
    fun `when invoke UseCase with invalid password`() {
        assertFailsWith<IllegalStateException> {
            register(username, "123")
        }
    }

    @Test
    fun `when invoke UseCase with invalid username and password`() {
        assertFailsWith<IllegalStateException> {
            register("123", "123")
        }
    }
}
