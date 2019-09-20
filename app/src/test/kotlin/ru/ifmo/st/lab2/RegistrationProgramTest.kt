package ru.ifmo.st.lab2

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.*
import ru.ifmo.st.lab2.framework.mock
import ru.ifmo.st.lab2.framework.runUITest
import ru.ifmo.st.lab2.program.*
import ru.ifmo.st.lab2.sample.makeFixedTask
import ru.ifmo.st.lab2.sample.sampleTask
import ru.ifmo.st.lab2.sl.create

class RegistrationProgramTest {

    @Test
    fun `test with successful registration`() = runUITest {
        program { it.create<RegistrationProgram>() }
        container {
            single<RegistrationUseCase> {
                mock {
                    on { invoke(any(), any()) } doReturn true
                }
            }
        }
        args = listOf("kek", "lol")
        constructOutput {
            single(RegistrationProgram.OK)
        }
    }

    @Test
    fun `test with unsuccessful registration`() = runUITest {
        program { it.create<RegistrationProgram>() }
        container {
            single<RegistrationUseCase> {
                mock {
                    on { invoke(any(), any()) } doReturn false
                }
            }
        }
        args = listOf("kek", "lol")
        constructOutput {
            single(RegistrationProgram.REG_ERROR)
        }
    }
}
