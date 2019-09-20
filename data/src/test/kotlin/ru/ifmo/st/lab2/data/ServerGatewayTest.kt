package ru.ifmo.st.lab2.data

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.data.ktor.KtorServerGateway
import ru.ifmo.st.lab2.gateway.ServerGateway

class ServerGatewayTest {
    companion object {
        lateinit var serverGateway: ServerGateway

        @BeforeAll
        @JvmStatic
        fun init() {
            serverGateway = KtorServerGateway()
        }
    }

    private val testEnvUsername = "username"
    private val testEnvPassword = "password"

//    @Test
//    fun `test valid login`() = runBlocking {
//        Assertions.assertTrue(serverGateway.login(testEnvUsername, testEnvPassword))
//    }
//
//    @Test
//    fun `test invalid login`() = runBlocking {
//        Assertions.assertFalse(serverGateway.login("loldaddsd", testEnvPassword))
//    }

}
