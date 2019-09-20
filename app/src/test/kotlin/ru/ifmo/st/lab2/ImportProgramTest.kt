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

class ImportProgramTest {

    @Test
    fun `test import with blank file name`() = runUITest {
        program { it.create<ImportProgram>() }
        container {
            mock<ImportDBUseCase>()
        }
        args(" ", ImportProgram.ADD_STRATEGY)
        constructOutput {
            single(ImportProgram.FILE_IS_EMPTY)
        }
    }

    @Test
    fun `test import with add strategy`() = runUITest {
        program { it.create<ImportProgram>() }
        container {
            mock<ImportDBUseCase>()
        }
        args("import.json", ImportProgram.ADD_STRATEGY)
        constructOutput {
            single(ImportProgram.OK)
        }
    }

    @Test
    fun `test import with own strategy`() = runUITest {
        program { it.create<ImportProgram>() }
        container {
            mock<ImportDBUseCase>()
        }
        args("import.json", ImportProgram.ACCEPT_OWN_STRATEGY)
        constructOutput {
            single(ImportProgram.OK)
        }
    }

    @Test
    fun `test import with their strategy`() = runUITest {
        program { it.create<ImportProgram>() }
        container {
            mock<ImportDBUseCase>()
        }
        args("import.json", ImportProgram.ACCEPT_THEIR_STRATEGY)
        constructOutput {
            single(ImportProgram.OK)
        }
    }

    @Test
    fun `test import with invalid strategy`() = runUITest {
        program { it.create<ImportProgram>() }
        container {
            mock<ImportDBUseCase>()
        }
        args("import.json", "error")
        constructOutput {
            single(ImportProgram.STRATEGY_ERROR)
        }
    }

}
