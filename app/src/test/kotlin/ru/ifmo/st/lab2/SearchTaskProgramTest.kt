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
import ru.ifmo.st.lab2.sample.makeSampleTask
import ru.ifmo.st.lab2.sample.sampleTask
import ru.ifmo.st.lab2.sl.create

class SearchTaskProgramTest {

    val tasks = listOf(
        makeSampleTask(1),
        makeSampleTask(2),
        makeSampleTask(3)
    )

    @Test
    fun `search with empty result`() = runUITest {
        program { it.create<SearchTaskProgram>() }
        container {
            single<SearchTasksByTagsUseCase> {
                mock {
                    on { invoke(any()) } doReturn emptyList()
                }
            }
        }
        args("docker,", " lol,", "kek")
        constructOutput {
            single(SearchTaskProgram.NOT_FOUND)
        }
    }

    @Test
    fun `search with not empty result`() = runUITest {
        program { it.create<SearchTaskProgram>() }
        container {
            single<SearchTasksByTagsUseCase> {
                mock {
                    on { invoke(any()) } doReturn tasks
                }
            }
        }
        args("docker,", " lol,", "kek")
        constructOutput {
            single(SearchTaskProgram.FOUND)
            many(tasks.map(Task::toView))
        }
    }


}
