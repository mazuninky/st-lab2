package ru.ifmo.st.lab2

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.DeleteTaskUseCase
import ru.ifmo.st.lab2.domain.FetchActualTaskUseCase
import ru.ifmo.st.lab2.domain.FetchNActualTaskUseCase
import ru.ifmo.st.lab2.domain.FindTaskByIdOrNameUseCase
import ru.ifmo.st.lab2.framework.mock
import ru.ifmo.st.lab2.framework.runUITest
import ru.ifmo.st.lab2.program.ActualListTaskProgram
import ru.ifmo.st.lab2.program.DeleteTaskProgram
import ru.ifmo.st.lab2.program.toView
import ru.ifmo.st.lab2.sample.makeFixedTask
import ru.ifmo.st.lab2.sample.sampleTask
import ru.ifmo.st.lab2.sl.create

class DeleteTaskProgramTest {

    @Test
    fun `test with invalid id`() = runUITest {
        program { it.create<DeleteTaskProgram>() }
        container {
            mock<DeleteTaskUseCase>()
            single<FindTaskByIdOrNameUseCase> {
                mock {
                    on { invoke(any()) } doReturn null
                }
            }
        }
        args = listOf("3")
        constructOutput {
            single(DeleteTaskProgram.ID_ERROR)
        }
    }

    @Test
    fun `test with valid id`() = runUITest {
        program { it.create<DeleteTaskProgram>() }
        container {
            mock<DeleteTaskUseCase>()
            single<FindTaskByIdOrNameUseCase> {
                mock {
                    on { invoke(any()) } doReturn sampleTask
                }
            }
        }
        args = listOf("3")
        constructOutput {
            single(DeleteTaskProgram.OK)
        }
    }
}
