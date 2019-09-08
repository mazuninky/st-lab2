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

class AddNewTaskProgram(private val useCase: AddNewTaskUseCase) : StateBaseProgram<AddNewTaskProgram.ProgramStage>() {
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

    override fun defineMachine() = StateMachine.create<ProgramStage> {
        setInitialState(ProgramStage.EnterName)
        state(ProgramStage.EnterName) {
            if (input.isBlank()) {
                showMessage("Имя не может быть пустым!")
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
            try {
                dueDate = dateFormat.parse(input)
                showMessage(ENTER_TAGS_MESSAGE)
                transitionTo(ProgramStage.EnterTags)
            } catch (e: NumberFormatException) {
                showMessage(WRONG_DUE_DATE_MESSAGE)
            } catch (e: ParseException) {
                showMessage(WRONG_DUE_DATE_MESSAGE)
            }
        }
        state(ProgramStage.EnterTags) {
            tags = input.split(",")
                    .map(String::trim)

            useCase.addNewTask(Task(name, description, dueDate, tags))
            finish()
        }
    }

    companion object {
        private const val ENTER_NAME_MESSAGE = "Введите название. Не может быть пустым."

        private const val ENTER_DESCRIPTION_MESSAGE = "Введите описание. Можно оставить пустым."

        private const val ENTER_DUE_DATE_MESSAGE = "Введите дедлайн в формате день-месяц-год (01-02-1998)"

        private const val WRONG_DUE_DATE_MESSAGE = "Неверный формат! Ожидаемый формат: дд-мм-гггг"
        private const val ENTER_TAGS_MESSAGE = "Введите теги через запятую"
    }

    private lateinit var dateFormat: SimpleDateFormat

    override fun afterDefineMachine() {
        dateFormat = SimpleDateFormat("dd-MM-yyy")
        showMessage(ENTER_NAME_MESSAGE)
    }
}
