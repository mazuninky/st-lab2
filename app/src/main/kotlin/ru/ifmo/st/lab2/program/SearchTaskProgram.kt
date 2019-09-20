package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.SearchTasksByTagsUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram

class SearchTaskProgram(private val search: SearchTasksByTagsUseCase) : ArgumentCommandProgram() {

    companion object {
        const val NOT_FOUND = "Ничего не найдено!"
        const val FOUND = "Найдено: "
    }

    override fun validateArgs(args: List<String>) = args.isNotEmpty()

    override fun afterStart() {
        val tags = args.joinToString(" ")
            .split(",")
            .map(String::trim)

        val tasks = search(tags.toList())
        if (tasks.isEmpty()) {
            showMessage(NOT_FOUND)
        } else {
            showMessage(FOUND)
            tasks
                .map(Task::toView)
                .forEach(this::showMessage)
        }
    }
}
