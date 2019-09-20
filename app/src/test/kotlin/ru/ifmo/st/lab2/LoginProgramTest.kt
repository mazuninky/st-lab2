package ru.ifmo.st.lab2

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.*
import ru.ifmo.st.lab2.framework.mock
import ru.ifmo.st.lab2.framework.runUITest
import ru.ifmo.st.lab2.program.ActualListTaskProgram
import ru.ifmo.st.lab2.program.DeleteTaskProgram
import ru.ifmo.st.lab2.program.LoginProgram
import ru.ifmo.st.lab2.program.toView
import ru.ifmo.st.lab2.sample.makeFixedTask
import ru.ifmo.st.lab2.sample.sampleTask
import ru.ifmo.st.lab2.sl.create

class LoginProgramTest {
    private val username = "username"
    private val password = "password"

    @Test
    fun `test with short username`() = runUITest {
        program { it.create<LoginProgram>() }
        container {
            single<LoginUseCase> {
                mock {
                    on { invoke(any(), any()) } doReturn true
                }
            }
        }
        args("1", password)
        constructOutput {
            single(LoginProgram.USERNAME_SHORT)
        }
    }

    @Test
    fun `test with short password`() = runUITest {
        program { it.create<LoginProgram>() }
        container {
            single<LoginUseCase> {
                mock {
                    on { invoke(any(), any()) } doReturn true
                }
            }
        }
        args(username, "123")
        constructOutput {
            single(LoginProgram.PASSWORD_SHORT)
        }
    }

    @Test
    fun `test with valid credits`() = runUITest {
        program { it.create<LoginProgram>() }
        container {
            single<LoginUseCase> {
                mock {
                    on { invoke(any(), any()) } doReturn true
                }
            }
        }
        args(username, password)
        constructOutput {
            single(LoginProgram.OK)
        }
    }

    @Test
    fun `test with invalid credits`() = runUITest {
        program { it.create<LoginProgram>() }
        container {
            single<LoginUseCase> {
                mock {
                    on { invoke(any(), any()) } doReturn false
                }
            }
        }
        args(username, password)
        constructOutput {
            single(LoginProgram.INVALID_CREDITS)
        }
    }
}
