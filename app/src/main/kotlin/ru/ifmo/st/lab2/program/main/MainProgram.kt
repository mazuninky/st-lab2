package ru.ifmo.st.lab2.program.main

import ru.ifmo.st.lab2.program.*

class MainProgram : BaseProgram() {
    companion object {
        const val ADD_TASK_COMMAND = "add"
        const val LIST_TASK_COMMAND = "list"
        const val ACTUAL_TASK_COMMAND = "actual"
        const val EXPORT_TASK_COMMAND = "export"
        const val EXIT_TASK_COMMAND = "exit"
        const val HELP_TASK_COMMAND = "help"
        const val EDIT_TASK_COMMAND = "edit"
        const val USER_COMMAND = "user"
        const val DELETE_TASK_COMMAND = "delete"
        const val TAG_COMMAND = "tags"
        const val SEARCH_TASK_COMMAND = "search"
        const val OVERDUE_TASK_COMMAND = "overdue"
        const val LOGIN_COMMAND = "login"
        const val SYNC_COMMAND = "sync"
        const val REGISTRATION_COMMAND = "registration"
        const val UNKNOWN_COMMAND = "Неизвестная команда"
        const val CREATE_MESSAGE = "TO-DO List"
    }


    override fun onCreate() {
        println(CREATE_MESSAGE)
        val helpParser = CSVParser("help.csv")
        context.setValue(HelpProgram.HELP_MAP_KEY, helpParser.parse())
    }

    override fun process(input: String) {
        val command = parseCommand(input.trim())

        when (command.name) {
            ADD_TASK_COMMAND -> context.startProgram<AddNewTaskProgram>()
            LIST_TASK_COMMAND -> context.startProgram<ListTaskProgram>()
            EXIT_TASK_COMMAND -> finish()
            HELP_TASK_COMMAND -> context.startProgram<HelpProgram>()
            EXPORT_TASK_COMMAND -> context.startProgram<ExportProgram>()
            ACTUAL_TASK_COMMAND -> context.startProgram<ActualListTaskProgram>()
            EDIT_TASK_COMMAND -> context.startProgram<EditTaskProgram>()
            DELETE_TASK_COMMAND -> context.startProgram<DeleteTaskProgram>()
            TAG_COMMAND -> context.startProgram<FetchTagsProgram>()
            SEARCH_TASK_COMMAND -> context.startProgram<SearchTaskProgram>()
            OVERDUE_TASK_COMMAND -> context.startProgram<ListOverdueTaskProgram>()
            USER_COMMAND -> context.startProgram<UserProgram>()
            LOGIN_COMMAND -> context.startProgram<LoginProgram>()
            SYNC_COMMAND -> context.startProgram<SyncProgram>()
            REGISTRATION_COMMAND -> context.startProgram<RegistrationProgram>()

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
