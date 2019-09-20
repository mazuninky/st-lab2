package ru.ifmo.st.lab2.framework

import ru.ifmo.st.lab2.program.BaseProgram
import ru.ifmo.st.lab2.program.Program
import ru.ifmo.st.lab2.program.TASK_ARGS_KEY
import ru.ifmo.st.lab2.sl.InjectableContainer

class StartProgramWithArgs(
    private val args: List<String>,
    private val programInit: (InjectableContainer) -> Program
) : BaseProgram() {
    override fun process(input: String) {
        context.setValue(TASK_ARGS_KEY, args)
        context.startProgram(programInit(context.container))
        finish()
    }
}
