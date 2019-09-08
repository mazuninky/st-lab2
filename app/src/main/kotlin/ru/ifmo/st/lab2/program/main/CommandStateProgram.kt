package ru.ifmo.st.lab2.program.main

import ru.ifmo.st.lab2.program.BaseProgram
import ru.ifmo.st.lab2.state.StateMachine

abstract class CommandStateProgram<State : Any> : BaseProgram() {
    private lateinit var args: List<String>
    private lateinit var machine: StateMachine<State>

    final override fun onCreate() {
        args = context.getValueOrDefault(TASK_ARGS_KEY, emptyList())
        machine = defineMachine()
        afterCreate()
    }

    protected abstract fun defineMachine(): StateMachine<State>

    protected fun afterCreate() {

    }

    final override suspend fun process(input: String) {
        machine.handle(input)
    }
}
