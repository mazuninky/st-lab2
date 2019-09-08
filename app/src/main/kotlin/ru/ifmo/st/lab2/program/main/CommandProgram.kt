package ru.ifmo.st.lab2.program.main

import ru.ifmo.st.lab2.program.BaseProgram

abstract class CommandProgram : BaseProgram() {
    protected lateinit var args: List<String>

    final override fun onCreate() {
        args = context.getValueOrDefault(TASK_ARGS_KEY, emptyList())
        afterCreate()
    }

    protected open fun afterCreate() {

    }
}
