package ru.ifmo.st.lab2.program.main

import ru.ifmo.st.lab2.program.BaseProgram
import ru.ifmo.st.lab2.state.StateMachine

abstract class StateBaseProgram<State : Any> : BaseProgram() {
    private lateinit var machine: StateMachine<State>

    final override fun onCreate() {
        machine = defineMachine()

        afterDefineMachine()
    }

    protected abstract fun defineMachine(): StateMachine<State>

    protected open fun afterDefineMachine() {

    }

    final override fun process(input: String) {
        machine.handle(input)
    }
}
