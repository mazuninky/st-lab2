package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.FetchNActualTaskUseCase
import java.lang.NumberFormatException
import java.lang.StringBuilder

class FetchNActuallTaskProgram(private val useCase: FetchNActualTaskUseCase) : BaseProgram() {
    companion object {
        val ErrorMessage = "Positive number expected"
    }


    override fun onCreate() {
        super.onCreate()
        showMessage("Enter N: ")
    }


    override suspend fun process(input: String) {

        try {
            val N = Integer.parseInt(input)

            if (N < 0) {
                showMessage(ErrorMessage)
                return
            }

            val tasks = useCase.fetch(N)

            tasks.forEach {
                val stringBuilder = StringBuilder()
                stringBuilder.append("Название: '${it.name}', ")
                if (it.description.isNotEmpty())
                    stringBuilder.append("Описание: '${it.description}', ")
                stringBuilder.append("Дедлайн: '${it.dueData}'")
                if (it.tags.isNotEmpty()) {
                    stringBuilder.append(", Теги: ")
                    stringBuilder.append(it.tags.joinToString(separator = ", "))
                }
                showMessage(stringBuilder.toString())
            }

            finish()
        } catch (e: NumberFormatException) {
            showMessage(ErrorMessage)
        }

    }
}
