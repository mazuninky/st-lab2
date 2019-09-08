package ru.ifmo.st.lab2.program.main

import ru.ifmo.st.lab2.program.*

class MainProgram : BaseProgram() {
    companion object {
        private const val ADD_TASK_COMMAND = "add"
        private const val FIND_TASK_COMMAND = "find"
        private const val LIST_TASK_COMMAND = "list"
        private const val EXIT_TASK_COMMAND = "exit"
        private const val HELP_TASK_COMMAND = "help"
        private const val UNKNOWN_COMMAND = "Неизвестная команда"
        private const val CREATE_MESSAGE = "TO-DO List"
//                "1. Добавление новой задачи\n" +
//                "2. Поиск задачи по тэгам\n" +
//                "3. Вывод N наиболее актуальных задач\n" +
//                "4. Выход из программы"
    }


    override fun onCreate() {
        println(CREATE_MESSAGE)
        val helpParser = CSVParser("help.csv")
        context.setValue(HelpProgram.HELP_MAP_KEY, helpParser.parse())
    }

    override suspend fun process(input: String) {
        val command = parseCommand(input.trim())

        when (command.name) {
            ADD_TASK_COMMAND -> context.startProgram<AddNewTaskProgram>()
            FIND_TASK_COMMAND -> finish()
            LIST_TASK_COMMAND -> context.startProgram<ListTaskProgram>()
            EXIT_TASK_COMMAND -> finish()
            HELP_TASK_COMMAND -> context.startProgram<HelpProgram>()
            else -> {
                showMessage(UNKNOWN_COMMAND)
                return
            }
        }

        context.setTaskArgs(command)
    }

    private fun parseCommand(input: String): Command {
        val split = input.split(" ")
        val commandName = split.first()

        val args = if (split.size > 1) split.subList(1, split.size) else emptyList()

        return Command(commandName, args)
    }
}
