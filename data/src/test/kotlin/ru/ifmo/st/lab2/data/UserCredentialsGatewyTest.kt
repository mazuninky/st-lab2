package ru.ifmo.st.lab2.data

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.core.Credentials
import ru.ifmo.st.lab2.data.ktor.KtorServerGateway
import ru.ifmo.st.lab2.gateway.ServerGateway
import ru.ifmo.st.lab2.gateway.UserCredentialsGatewy

class UserCredentialsGatewyTest {
    companion object {
        lateinit var userCredentialsGatewy: UserCredentialsGatewy

        @BeforeAll
        @JvmStatic
        fun init() {
            userCredentialsGatewy = MemoryUserCredentialsGatewy()
        }
    }

    private val credits = Credentials("username", "password")

    @Test
    fun `test store and get credits`() {
        userCredentialsGatewy.store(credits)
        val actual = userCredentialsGatewy.fetch()

        Assertions.assertEquals(credits, actual)
    }


}
