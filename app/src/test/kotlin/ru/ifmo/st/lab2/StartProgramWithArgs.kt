package ru.ifmo.st.lab2

import ru.ifmo.st.lab2.program.BaseProgram
import ru.ifmo.st.lab2.program.TASK_ARGS_KEY

class StartProgramWithArgs(private val args: List<String>) : BaseProgram() {
    override fun onCreate() {

    }
    override fun process(input: String) {
        context.setValue(TASK_ARGS_KEY, args)

    }
}
