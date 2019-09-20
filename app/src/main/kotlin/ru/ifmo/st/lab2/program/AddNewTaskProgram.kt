package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.AddNewTaskUseCase
import ru.ifmo.st.lab2.program.main.StateBaseProgram
import ru.ifmo.st.lab2.state.StateMachine
import java.lang.NumberFormatException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors

class AddNewTaskProgram(private val addNewTask: AddNewTaskUseCase) :
    StateBaseProgram<AddNewTaskProgram.ProgramStage>() {
    sealed class ProgramStage {
        object EnterName : ProgramStage()
        object EnterDescription : ProgramStage()
        object EnterDueDate : ProgramStage()
        object EnterTags : ProgramStage()
    }

    private lateinit var name: String
    private lateinit var description: String
    private lateinit var dueDate: Date
    private lateinit var tags: List<String>

    companion object {
        const val ENTER_NAME_MESSAGE = "Введите название. Не может быть пустым."
        const val EMPTY_NAME = "Имя не может быть пустым!"
        const val ENTER_DESCRIPTION_MESSAGE = "Введите описание. Можно оставить пустым."

        const val ENTER_DUE_DATE_MESSAGE = "Введите дедлайн в формате день-месяц-год (01-02-1998)"

        const val WRONG_DUE_DATE_MESSAGE = "Неверный формат! Ожидаемый формат: дд-мм-гггг"
        const val ENTER_TAGS_MESSAGE = "Введите теги через запятую"

        const val OK = "Задача создана!"
    }

    override fun defineMachine() = StateMachine.create<ProgramStage> {
        setInitialState(ProgramStage.EnterName)
        state(ProgramStage.EnterName) {
            if (input.isBlank()) {
                showMessage(EMPTY_NAME)
                return@state
            }
            name = input
            showMessage(ENTER_DESCRIPTION_MESSAGE)
            transitionTo(ProgramStage.EnterDescription)
        }
        state(ProgramStage.EnterDescription) {
            description = input
            showMessage(ENTER_DUE_DATE_MESSAGE)
            transitionTo(ProgramStage.EnterDueDate)
        }
        state(ProgramStage.EnterDueDate) {
            val parsedDate = dateFormat.parseOrNull(input)
            if (parsedDate != null) {
                dueDate = parsedDate
                showMessage(ENTER_TAGS_MESSAGE)
                transitionTo(ProgramStage.EnterTags)
            } else {
                showMessage(WRONG_DUE_DATE_MESSAGE)
            }
        }
        state(ProgramStage.EnterTags) {
            tags = input.split(",")
                .map(String::trim)

            addNewTask(Task(name, description, dueDate, tags))
            showMessage(OK)
            finish()
        }
    }

    private lateinit var dateFormat: SimpleDateFormat

    override fun afterDefineMachine() {
        dateFormat = SimpleDateFormat("dd-MM-yyy")
        showMessage(ENTER_NAME_MESSAGE)
    }
}
