package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram

class HelpProgram : ArgumentCommandProgram() {
    companion object {
        const val HELP_MAP_KEY = "help_map"
    }

    override fun validateArgs(args: List<String>) = args.size <= 1

    override fun afterStart() {
        val commandMap = context.getValue<Map<String, String>>(HELP_MAP_KEY)

        if (args.isEmpty())
            showMessage("Доступные команды: ${commandMap.keys.joinToString(", ")}")
        else {
            val commandName = args.first()
            if (!commandMap.contains(commandName)) {
                showMessage("Неверное название команды")
                return
            }
            showMessage(commandMap.getValue(commandName))
        }
    }
}
