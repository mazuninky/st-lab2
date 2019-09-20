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
import ru.ifmo.st.lab2.sample.makeActualTask
import ru.ifmo.st.lab2.sl.create

class EditTaskProgramTest {

    @Test
    fun `abort edit task with empty, invalid and valid id`() = runUITest {
        program { it.create<EditTaskProgram>() }
        container {
            mock<UpdateTaskUseCase>()
            single<FindTaskByIdOrNameUseCase> {
                mock {
                    on { invoke(any()) } doReturn null doReturn makeActualTask(1)
                }
            }
        }
        story {
            output(EditTaskProgram.ENTER_ID_OR_NAME)
            input("")
            output(EditTaskProgram.ID_EMPTY)
            input("error")
            output(EditTaskProgram.ID_ERROR)
            input("1")
            output(EditTaskProgram.ENTER_CHANGE)
            input(EditTaskProgram.ABORT)
            output(EditTaskProgram.ABORT_MESSAGE)
        }
    }

    @Test
    fun `save edit task with checking constraints`() = runUITest {
        program { it.create<EditTaskProgram>() }
        container {
            mock<UpdateTaskUseCase>()
            single<FindTaskByIdOrNameUseCase> {
                mock {
                    on { invoke(any()) } doReturn makeActualTask(1)
                }
            }
        }
        story {
            output(EditTaskProgram.ENTER_ID_OR_NAME)
            input("1")

            output(EditTaskProgram.ENTER_CHANGE)

            input("   ")
            output(EditTaskProgram.COMMAND_EMPTY)

            input(EditTaskProgram.NAME, "12345")

            input(EditTaskProgram.DESCRIPTION, "12345")
            input(EditTaskProgram.DESCRIPTION, " ")

            input(EditTaskProgram.DATE, "123")
            output(EditTaskProgram.DATE_ERROR)
            input(EditTaskProgram.DATE, "01-02-1998")

            input(EditTaskProgram.STATE, EditTaskProgram.STATE_BACKLOG)
            input(EditTaskProgram.STATE, EditTaskProgram.STATE_WORKING)
            input(EditTaskProgram.STATE, EditTaskProgram.STATE_DONE)
            input(EditTaskProgram.STATE, "dads")
            output(EditTaskProgram.STATE_ERROR)

            input("dueDate", "01-02-1998")
            output(EditTaskProgram.UNKNOWN)

            input(EditTaskProgram.TAGS, "docker, lol, kek")

            input(EditTaskProgram.SAVE)
            output(EditTaskProgram.OK)
        }
    }
}
