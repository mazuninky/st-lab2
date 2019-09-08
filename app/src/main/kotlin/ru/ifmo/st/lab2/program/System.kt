package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.program.main.Command

const val TASK_ARGS_KEY = "args"

fun Context.setTaskArgs(command: Command) {
    clearValue(TASK_ARGS_KEY)
    setValue(TASK_ARGS_KEY, command.args)
}
