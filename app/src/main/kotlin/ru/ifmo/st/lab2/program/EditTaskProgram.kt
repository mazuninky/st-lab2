package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.AddNewTaskUseCase
import ru.ifmo.st.lab2.domain.FindTaskByIdOrNameUseCase
import ru.ifmo.st.lab2.domain.UpdateTaskUseCase
import ru.ifmo.st.lab2.program.main.StateBaseProgram
import ru.ifmo.st.lab2.state.StateMachine
import java.lang.NumberFormatException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EditTaskProgram(private val update: UpdateTaskUseCase, private val findByIdOrName: FindTaskByIdOrNameUseCase) : StateBaseProgram<EditTaskProgram.ProgramState>() {
    sealed class ProgramState {
        object EnterNameOrId : ProgramState()
        object EnterChange : ProgramState()
    }

    private lateinit var task: Task

    private fun <T> List<T>.second(): T {
        if (isEmpty())
            throw NoSuchElementException("List is empty.")
        return this[1]
    }

    override fun defineMachine() = StateMachine.create<ProgramState> {
        setInitialState(ProgramState.EnterNameOrId)
        state(ProgramState.EnterNameOrId) {
            check(input.isBlank())
//            if (input.isBlank()) {
//                showMessage("Значение не может быть пустым!")
//                return@state
//            }

            val foundTask = findByIdOrName(input)
            if (foundTask == null) {
                showMessage("Нет такого индентификатора или невозможно однозначно определить задание!")
                return@state
            }

            task = foundTask

            showMessage("Введите, что хотите изменить")
            transitionTo(ProgramState.EnterChange)
        }

        state(ProgramState.EnterChange) {
            val split = input.split(" ")

            check(split.isNotEmpty())

            if (split.size == 1) {
                when (split.first()) {
                    "abort" -> finish()
                    "save" -> updateTask()
                    else -> showMessage("Неизвестная команда!")
                }
                return@state
            }

            when (split.first()) {
                "name" -> {
                    task.name = split.subList(1, split.size).joinToString(separator = " ")
                }
                "description" -> {
                    task.description = split.subList(1, split.size).joinToString(separator = " ")
                }
                "date" -> {
                    val parsedDate = dateFormat.parseOrNull(input)
                    if (parsedDate != null) {
                        task.dueData = parsedDate
                    } else {
                        showMessage("Введите дедлайн в формате день-месяц-год (01-02-1998)!")
                    }
                }
                "tags" -> {
                    task.tags = split.subList(1, split.size)
                }


                else -> showMessage("Неизвестная команда!")
            }
        }
    }

    private fun updateTask() {
        update(task)
    }

    private lateinit var dateFormat: SimpleDateFormat

    override fun afterDefineMachine() {
        dateFormat = SimpleDateFormat("dd-MM-yyy")
        showMessage("Введите название или id задания: ")
    }
}
