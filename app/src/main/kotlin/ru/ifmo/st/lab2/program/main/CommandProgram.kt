package ru.ifmo.st.lab2.program.main

import ru.ifmo.st.lab2.program.BaseProgram
import ru.ifmo.st.lab2.program.TASK_ARGS_KEY

abstract class CommandProgram : BaseProgram() {
    private val ARGUMENT_SIZE_MISTMATCH = "Неверное количество аргументов. Для справки вызови help <команда>"

    protected lateinit var args: List<String>

    final override fun onCreate() {
        args = context.getValueOrDefault(TASK_ARGS_KEY, emptyList())
        afterCreate()
    }

    protected open fun afterCreate() {

    }

    final override fun onStart() {
        if (!validateArgs(args)) {
            showMessage(ARGUMENT_SIZE_MISTMATCH)
            finish()
            return
        }

        afterStart()
    }

    protected open fun afterStart() {

    }

    protected open fun validateArgs(args: List<String>): Boolean {
        return true
    }

}
