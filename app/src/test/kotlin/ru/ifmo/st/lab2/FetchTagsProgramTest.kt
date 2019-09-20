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
import ru.ifmo.st.lab2.program.FetchTagsProgram
import ru.ifmo.st.lab2.program.ListTaskProgram
import ru.ifmo.st.lab2.program.toView
import ru.ifmo.st.lab2.sample.makeFixedTask
import ru.ifmo.st.lab2.sl.create
import kotlin.math.sin

class FetchTagsProgramTest {

    private val tags = listOf(
        "test", "docker", "sample"
    )

    @Test
    fun `when tags is empty`() = runUITest {
        program { it.create<FetchTagsProgram>() }
        container {
            single<FetchTagsUseCase> {
                mock {
                    on { invoke() } doReturn emptyList()
                }
            }
        }
        constructOutput {
            single(FetchTagsProgram.EMPTY)
        }
    }

    @Test
    fun `when tags is not empty`() = runUITest {
        program { it.create<FetchTagsProgram>() }
        container {
            single<FetchTagsUseCase> {
                mock {
                    on { invoke() } doReturn tags
                }
            }
        }
        constructOutput {
            single(FetchTagsProgram.HEADER)
            single(tags.joinToString(", "))
        }
    }


}
