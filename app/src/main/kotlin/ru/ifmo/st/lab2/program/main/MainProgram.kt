package ru.ifmo.st.lab2.program.main

import ru.ifmo.st.lab2.program.*

class MainProgram : BaseProgram() {
    companion object {
        private const val ADD_TASK_COMMAND = "add"
        private const val FIND_TASK_COMMAND = "find"
        private const val LIST_TASK_COMMAND = "list"
        private const val ACTUAL_TASK_COMMAND = "actual"
        private const val EXPORT_TASK_COMMAND = "export"
        private const val EXIT_TASK_COMMAND = "exit"
        private const val HELP_TASK_COMMAND = "help"
        private const val EDIT_TASK_COMMAND = "edit"
        private const val DELETE_TASK_COMMAND = "delete"
        private const val TAG_COMMAND = "tag"
        private const val UNKNOWN_COMMAND = "Неизвестная команда"
        private const val CREATE_MESSAGE = "TO-DO List"
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
            EXPORT_TASK_COMMAND -> context.startProgram<ExportProgram>()
            ACTUAL_TASK_COMMAND -> context.startProgram<ActualListTaskProgram>()
            EDIT_TASK_COMMAND -> context.startProgram<EditTaskProgram>()
            DELETE_TASK_COMMAND -> context.startProgram<DeleteTaskProgram>()
            TAG_COMMAND -> context.startProgram<FetchTagsProgram>()
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
