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
import ru.ifmo.st.lab2.sample.makeOutdatedTask
import ru.ifmo.st.lab2.sl.create
import kotlin.math.sin

class ListOverdueTaskProgramTest {

    private val tasks = listOf(
        makeOutdatedTask(1),
        makeOutdatedTask(2),
        makeOutdatedTask(3)
    )

    @Test
    fun `when tasks is empty`() = runUITest {
        program { it.create<ListOverdueTaskProgram>() }
        container {
            single<FetchOverdueTaskUseCase> {
                mock {
                    on { invoke() } doReturn emptyList()
                }
            }
        }
        constructOutput {
            single(ListOverdueTaskProgram.EMPTY)
        }
    }

    @Test
    fun `when tags is not empty`() = runUITest {
        program { it.create<ListOverdueTaskProgram>() }
        container {
            single<FetchOverdueTaskUseCase> {
                mock {
                    on { invoke() } doReturn tasks
                }
            }
        }
        constructOutput {
            single(ListOverdueTaskProgram.HEAD)
            many { tasks.map(Task::toView) }
        }
    }


}
