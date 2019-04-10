package ru.ifmo.st.lab2.program.main

import ru.ifmo.st.lab2.program.AddNewTaskProgram
import ru.ifmo.st.lab2.program.BaseProgram

class MainProgram : BaseProgram() {
    companion object {
        private const val ADD_TASK_COMMAND = "1"
        private const val FIND_TASK_COMMAND = "2"
        private const val LIST_TASK_COMMAND = "3"
        private const val EXIT_TASK_COMMAND = "4"
        private const val CREATE_MESSAGE = "TO-DO List\n" +
                "1. Добавление новой задачи\n" +
                "2. Поиск задачи по тэгам\n" +
                "3. Вывод N наиболее актуальных задач\n" +
                "4. Выход из программы"
    }

    override fun onStart() {
        println(CREATE_MESSAGE)
        //printHelp()
    }

    override fun process(input: String) {
        val command = parseCommand(input.trim())
        when (command.name) {
            ADD_TASK_COMMAND -> context.startProgram<AddNewTaskProgram>()
            FIND_TASK_COMMAND -> finish()
            LIST_TASK_COMMAND -> finish()
            EXIT_TASK_COMMAND -> finish()
        }
    }

    private fun parseCommand(input: String): Command {
        val split = input.split(" ")
        val commandName = split.first()

        val args = if (split.size > 1) split.subList(1, split.size) else emptyList()

        return Command(commandName, args)
    }
}
