package ru.ifmo.st.lab2.framework

import ru.ifmo.st.lab2.program.BaseProgram
import ru.ifmo.st.lab2.program.Program
import ru.ifmo.st.lab2.program.Request
import ru.ifmo.st.lab2.program.TASK_ARGS_KEY
import ru.ifmo.st.lab2.sl.InjectableContainer

class StartProgramWithArgs(
    private val args: List<String> = emptyList(),
    private val env: Map<String, Any> = emptyMap(),
    private val programInit: (InjectableContainer) -> Program
) : BaseProgram() {
    override fun process(input: String) {
        context.setValue(TASK_ARGS_KEY, args)
        context.setMap(env)
        context.startProgram(programInit(context.container))
        finish()
    }
}
