package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.AddNewTaskUseCase

class AddNewTaskProgram(private val useCase: AddNewTaskUseCase) : BaseProgram() {
    companion object {
        private const val ENTER_NAME_MESSAGE = "Введите название"
        private const val ENTER_DESCRIPTION_MESSAGE = "Введите описание"
        private const val ENTER_DUE_DATE_MESSAGE = "Введите описание"
    }

    private var state: ProgramStage = ProgramStage.Init

    override fun onCreate() {
        showMessage(ENTER_NAME_MESSAGE)
        state = ProgramStage.EnterName
    }

    override fun onStart() {
        // finish()
    }

    private lateinit var name: String
    private lateinit var description: String

    override suspend fun process(input: String) {
        when (state) {
            ProgramStage.EnterName -> {
                name = input
                showMessage(ENTER_DESCRIPTION_MESSAGE)
                state = ProgramStage.EnterDescription
            }

            ProgramStage.EnterDescription -> {
                description = input
                showMessage()
            }
        }

    }

    private enum class ProgramStage {
        Init, EnterName, EnterDescription, EnterDueDate, EnterTags
    }
}
