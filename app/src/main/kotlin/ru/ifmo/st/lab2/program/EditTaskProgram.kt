package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.core.TaskState
import ru.ifmo.st.lab2.domain.AddNewTaskUseCase
import ru.ifmo.st.lab2.domain.FindTaskByIdOrNameUseCase
import ru.ifmo.st.lab2.domain.UpdateTaskUseCase
import ru.ifmo.st.lab2.program.main.StateBaseProgram
import ru.ifmo.st.lab2.state.StateMachine
import java.lang.NumberFormatException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EditTaskProgram(
    private val update: UpdateTaskUseCase,
    private val findByIdOrName: FindTaskByIdOrNameUseCase
) : StateBaseProgram<EditTaskProgram.ProgramState>() {
    sealed class ProgramState {
        object EnterNameOrId : ProgramState()
        object EnterChange : ProgramState()
    }

    private lateinit var task: Task

    companion object {
        const val ENTER_ID_OR_NAME = "Введите название или id задания: "
        const val ID_EMPTY = "Значение не может быть пустым!"
        const val ID_ERROR = "Нет такого индентификатора или невозможно однозначно определить задание!"
        const val ENTER_CHANGE = "Введите, что хотите изменить"

        const val UNKNOWN = "Неизвестная команда!"
        const val ABORT = "abort"
        const val SAVE = "save"

        const val COMMAND_EMPTY = "Необходимо ввести <название_поля> <значени>"

        const val STATE = "state"
        const val STATE_BACKLOG = "backlog"
        const val STATE_WORKING = "working"
        const val STATE_DONE = "done"
        const val STATE_ERROR = "Неверное название состояния!"

        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val DATE = "date"
        const val DATE_ERROR = "Введите дедлайн в формате день-месяц-год (01-02-1998)!"
        const val TAGS = "tags"

        const val ABORT_MESSAGE = "Изменения не сохранены"
        const val OK = "Изменения сохранены"
    }

    override fun defineMachine() = StateMachine.create<ProgramState> {
        setInitialState(ProgramState.EnterNameOrId)
        state(ProgramState.EnterNameOrId) {
            if (input.isBlank()) {
                showMessage(ID_EMPTY)
                return@state
            }

            val foundTask = findByIdOrName(input)
            if (foundTask == null) {
                showMessage(ID_ERROR)
                return@state
            }

            task = foundTask

            showMessage(ENTER_CHANGE)
            transitionTo(ProgramState.EnterChange)
        }

        state(ProgramState.EnterChange) {
            val split = input.trim().split(" ")
            if (split.first().isEmpty()) {
                showMessage(COMMAND_EMPTY)
                return@state
            }

            if (split.size == 1) {
                when (split.first()) {
                    ABORT -> {
                        showMessage(ABORT_MESSAGE)
                        finish()
                    }
                    SAVE -> {
                        updateTask()
                        showMessage(OK)
                        finish()
                    }
                    DESCRIPTION -> {
                        task.description = ""
                    }
                    else -> showMessage(UNKNOWN)
                }
                return@state
            }

            when (split.first()) {
                STATE -> {
                    val state = when (split.second()) {
                        STATE_BACKLOG -> TaskState.Backlog
                        STATE_WORKING -> TaskState.InProgress
                        STATE_DONE -> TaskState.Done
                        else -> null
                    }

                    if (state == null) {
                        showMessage(STATE_ERROR)
                    } else {
                        task.state = state
                    }
                }
                NAME -> {
                    task.name = split.subList(1, split.size).joinToString(separator = " ")
                }
                DESCRIPTION -> {
                    task.description = split.subList(1, split.size).joinToString(separator = " ")
                }
                DATE -> {
                    val parsedDate = dateFormat.parseOrNull(split.second())
                    if (parsedDate != null) {
                        task.dueData = parsedDate
                    } else {
                        showMessage(DATE_ERROR)
                    }
                }
                TAGS -> {
                    task.tags = split.subList(1, split.size)
                        .joinToString(" ")
                        .split(",")
                        .map(String::trim)
                }


                else -> showMessage(UNKNOWN)
            }
        }
    }

    private fun updateTask() {
        update(task)
    }

    private lateinit var dateFormat: SimpleDateFormat

    override fun afterDefineMachine() {
        dateFormat = SimpleDateFormat("dd-MM-yyy")
        showMessage(ENTER_ID_OR_NAME)
    }
}
