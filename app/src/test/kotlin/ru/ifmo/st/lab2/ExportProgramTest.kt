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

class ExportProgramTest {

    @Test
    fun `test export`() = runUITest {
        program { it.create<ExportProgram>() }
        container {
            mock<ExportDBUseCase>()
        }
        args("export.json")
        constructOutput {
            single(ExportProgram.OK)
        }
    }

    @Test
    fun `test export with blank file name`() = runUITest {
        program { it.create<ExportProgram>() }
        container {
            mock<ExportDBUseCase>()
        }
        args("   ")
        constructOutput {
            single(ExportProgram.FILE_IS_EMPTY)
        }
    }


}
