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
        args = listOf("kek", "lol")
        constructOutput {
            single(LoginProgram.OK)
        }
    }

    @Test
    fun `test with valid id`() = runUITest {
        program { it.create<LoginProgram>() }
        container {
            single<LoginUseCase> {
                mock {
                    on { invoke(any(), any()) } doReturn false
                }
            }
        }
        args = listOf("kek", "lol")
        constructOutput {
            single(LoginProgram.INVALID_CREDITS)
        }
    }
}
