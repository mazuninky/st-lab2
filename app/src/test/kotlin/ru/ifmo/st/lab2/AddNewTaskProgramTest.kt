package ru.ifmo.st.lab2

import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.domain.AddNewTaskUseCase
import ru.ifmo.st.lab2.framework.mock
import ru.ifmo.st.lab2.framework.runUITest
import ru.ifmo.st.lab2.program.AddNewTaskProgram
import ru.ifmo.st.lab2.sl.create

class AddNewTaskProgramTest {

    @Test
    fun `add new task with checking constraints`() = runUITest {
        program { it.create<AddNewTaskProgram>() }
        container { mock<AddNewTaskUseCase>() }
        story {
            output(AddNewTaskProgram.ENTER_NAME_MESSAGE)
            input("")
            output(AddNewTaskProgram.EMPTY_NAME)
            input("abcd")
            output(AddNewTaskProgram.ENTER_DESCRIPTION_MESSAGE)
            input("")
            output(AddNewTaskProgram.ENTER_DUE_DATE_MESSAGE)
            input("21")
            output(AddNewTaskProgram.WRONG_DUE_DATE_MESSAGE)
            input("01-02-1998")
            output(AddNewTaskProgram.ENTER_TAGS_MESSAGE)
            input("test, docker, sample")
            output(AddNewTaskProgram.OK)
        }
    }
}
