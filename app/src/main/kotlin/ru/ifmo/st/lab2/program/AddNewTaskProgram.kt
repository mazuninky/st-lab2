package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.AddNewTaskUseCase
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors

class AddNewTaskProgram(private val useCase: AddNewTaskUseCase) : BaseProgram() {
    companion object {
        private const val ENTER_NAME_MESSAGE = "Введите название. Не может быть пустым."
        private const val ENTER_DESCRIPTION_MESSAGE = "Введите описание. Можно оставить пустым."
        private const val ENTER_DUE_DATE_MESSAGE = "Введите дедлайн в формате день-месяц-год (01-02-1998)"
        private const val WRONG_DUE_DATE_MESSAGE = "Неверный формат! Ожидаемый формат: дд-мм-гггг"
        private const val ENTER_TAGS_MESSAGE = "Введите теги через запятую"
    }

    private var state: ProgramStage = ProgramStage.Init
    private lateinit var dateFormat: SimpleDateFormat

    override fun onCreate() {
        dateFormat = SimpleDateFormat("dd-MM-yyy")
        showMessage(ENTER_NAME_MESSAGE)
        state = ProgramStage.EnterName
    }

    override fun onStart() {
        // finish()
    }

    private lateinit var name: String
    private lateinit var description: String
    private lateinit var dueDate: Date
    private lateinit var tags: List<String>

    override suspend fun process(input: String) {
        when (state) {
            ProgramStage.EnterName -> {
                name = input
                showMessage(ENTER_DESCRIPTION_MESSAGE)
                state = ProgramStage.EnterDescription
            }

            ProgramStage.EnterDescription -> {
                description = input
                showMessage(ENTER_DUE_DATE_MESSAGE)
                state = ProgramStage.EnterDueDate
            }

            ProgramStage.EnterDueDate -> {
                try {
                    dueDate = dateFormat.parse(input)
                    showMessage(ENTER_TAGS_MESSAGE)
                    state = ProgramStage.EnterTags
                } catch (e : NumberFormatException) {
                    showMessage(WRONG_DUE_DATE_MESSAGE)
                }
            }

            ProgramStage.EnterTags -> {
                tags = input.split(",").stream().map(String::trim).collect(Collectors.toList())

                useCase.addNewTask(Task(name, description, dueDate, tags))
                finish()
            }
        }

    }

    private enum class ProgramStage {
        Init, EnterName, EnterDescription, EnterDueDate, EnterTags
    }
}
