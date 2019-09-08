package ru.ifmo.st.lab2.app

import ru.ifmo.st.lab2.module.appContainer
import ru.ifmo.st.lab2.program.ProgramFramework
import ru.ifmo.st.lab2.program.main.MainProgram

suspend fun main() =
        ProgramFramework(MainProgram(), container = appContainer)
                .run()
