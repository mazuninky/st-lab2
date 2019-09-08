package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.program.main.CommandProgram

class HelpTaskProgram : CommandProgram() {
    companion object {
        const val HELP_MAP_KEY = "help_map"
    }

    override fun afterCreate() {
        val commandMap = context.getValue<Map<String, String>>(HELP_MAP_KEY)

        if (args.isEmpty() || args.size > 1) {
            showMessage("Укажитие команду для справки. help <имя команды>")
            return
        }

        val commandName = args.first()
        if (!commandMap.contains(commandName)) {
            showMessage("Неверное название команды")
            return
        }

        showMessage(commandMap.getValue(commandName))
    }

    override fun onStart() {
        finish()
    }

    override suspend fun process(input: String) {

    }
}
