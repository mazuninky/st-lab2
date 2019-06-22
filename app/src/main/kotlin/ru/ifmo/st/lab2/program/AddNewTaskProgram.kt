package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.AddNewTaskUseCase

class AddNewTaskProgram : BaseProgram() {
    companion object {
        private const val ENTER_NAME_MESSAGE = "Введите название"
    }

    private lateinit var useCase: AddNewTaskUseCase

    override fun onCreate() {
        println(ENTER_NAME_MESSAGE)
    }

    override fun onStart() {
        finish()
    }

    override suspend fun process(input: String) {

    }
}
