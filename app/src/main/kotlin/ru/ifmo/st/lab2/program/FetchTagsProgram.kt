package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.FetchActualTaskUseCase
import ru.ifmo.st.lab2.domain.FetchNActualTaskUseCase
import ru.ifmo.st.lab2.domain.FetchTagsUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram
import ru.ifmo.st.lab2.program.main.CommandProgram

class FetchTagsProgram(private val fetchTags: FetchTagsUseCase) : ArgumentCommandProgram() {

    companion object {
        const val EMPTY = "Нет тегов!"
        const val HEADER = "Список тэгов: "
    }

    override fun validateArgs(args: List<String>) = args.size <= 1

    override fun afterStart() {
        val tags = fetchTags()
        if (tags.isEmpty()) {
            showMessage(EMPTY)
            return
        }

        showMessage(HEADER)
        showMessage(tags.joinToString(", "))
    }
}
