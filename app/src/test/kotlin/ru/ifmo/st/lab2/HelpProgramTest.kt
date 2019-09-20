package ru.ifmo.st.lab2

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.domain.AddNewTaskUseCase
import ru.ifmo.st.lab2.domain.FindTaskByIdOrNameUseCase
import ru.ifmo.st.lab2.domain.UpdateTaskUseCase
import ru.ifmo.st.lab2.framework.mock
import ru.ifmo.st.lab2.framework.runUITest
import ru.ifmo.st.lab2.program.AddNewTaskProgram
import ru.ifmo.st.lab2.program.EditTaskProgram
import ru.ifmo.st.lab2.program.HelpProgram
import ru.ifmo.st.lab2.sample.makeActualTask
import ru.ifmo.st.lab2.sl.create

class HelpProgramTest {

    private val helpMap = mapOf(
        "help" to "help description",
        "edit" to "edit description",
        "list" to "list description"
    )

    @Test
    fun `run without args`() = runUITest {
        program { it.create<HelpProgram>() }
        env {
            set(HelpProgram.HELP_MAP_KEY) { helpMap }
        }
        story {
            output(HelpProgram.AVAILABLE_COMMANDS)
            output("help, edit, list")
        }
    }

    @Test
    fun `run with valid arg`() = runUITest {
        program { it.create<HelpProgram>() }
        env {
            set(HelpProgram.HELP_MAP_KEY) { helpMap }
        }
        args("help")
        story {
            output("help description")
        }
    }

    @Test
    fun `run with unknown command arg`() = runUITest {
        program { it.create<HelpProgram>() }
        env {
            set(HelpProgram.HELP_MAP_KEY) { helpMap }
        }
        args("add")
        story {
            output(HelpProgram.INVALID_COMMAND)
        }
    }
}
