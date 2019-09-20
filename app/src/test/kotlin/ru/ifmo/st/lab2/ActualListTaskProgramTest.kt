package ru.ifmo.st.lab2

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.FetchActualTaskUseCase
import ru.ifmo.st.lab2.domain.FetchNActualTaskUseCase
import ru.ifmo.st.lab2.framework.mock
import ru.ifmo.st.lab2.framework.runUITest
import ru.ifmo.st.lab2.program.ActualListTaskProgram
import ru.ifmo.st.lab2.program.toView
import ru.ifmo.st.lab2.sample.makeFixedTask
import ru.ifmo.st.lab2.sl.create

class ActualListTaskProgramTest {

    private val taskList = listOf(
        makeFixedTask(1),
        makeFixedTask(2),
        makeFixedTask(3)
    )

    @Test
    fun `test empty task list without arguments`() = runUITest {
        program { it.create<ActualListTaskProgram>() }
        container {
            single<FetchActualTaskUseCase> {
                mock {
                    on { invoke() } doReturn emptyList()
                }
            }
            single<FetchNActualTaskUseCase> { mock { } }
        }
        constructOutput {
            single(ActualListTaskProgram.Empty)
        }
    }

    @Test
    fun `test task list without arguments`() = runUITest {
        program { it.create<ActualListTaskProgram>() }
        container {
            single<FetchActualTaskUseCase> {
                mock {
                    on { invoke() } doReturn taskList
                }
            }
            mock<FetchNActualTaskUseCase>()
        }
        constructOutput {
            single(ActualListTaskProgram.List)
            many { taskList.map(Task::toView) }
        }
    }

    @Test
    fun `test task list with positive argument`() = runUITest {
        program { it.create<ActualListTaskProgram>() }
        container {
            mock<FetchActualTaskUseCase>()
            single<FetchNActualTaskUseCase> {
                mock {
                    on { invoke(any()) } doReturn taskList
                }
            }
        }
        constructArgs {
            single("3")
        }
        constructOutput {
            single(ActualListTaskProgram.List)
            many { taskList.map(Task::toView) }
        }
    }

    @Test
    fun `test task list with zero argument`() = runUITest {
        program { it.create<ActualListTaskProgram>() }
        container {
            mock<FetchActualTaskUseCase>()
            single<FetchNActualTaskUseCase> {
                mock {
                    on { invoke(any()) } doReturn emptyList()
                }
            }
        }
        constructArgs {
            single("0")
        }
        constructOutput {
            single(ActualListTaskProgram.Empty)
        }
    }

    @Test
    fun `test task list with negative argument`() = runUITest {
        program { it.create<ActualListTaskProgram>() }
        container {
            mock<FetchActualTaskUseCase>()
            mock<FetchNActualTaskUseCase>()
        }
        constructArgs {
            single("-1")
        }
        constructOutput {
            single(ActualListTaskProgram.LimitErrorMessage)
        }
    }

    @Test
    fun `test task list with string argument`() = runUITest {
        program { it.create<ActualListTaskProgram>() }
        container {
            mock<FetchActualTaskUseCase>()
            mock<FetchNActualTaskUseCase>()
        }
        constructArgs {
            single("dssaddsa")
        }
        constructOutput {
            single(ActualListTaskProgram.LimitErrorMessage)
        }
    }

}
