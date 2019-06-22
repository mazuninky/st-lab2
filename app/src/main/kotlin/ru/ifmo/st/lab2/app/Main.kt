package ru.ifmo.st.lab2.app

import ru.ifmo.st.lab2.module.appContainer
import ru.ifmo.st.lab2.program.AddNewTaskProgram
import ru.ifmo.st.lab2.program.ProgramFramework
import ru.ifmo.st.lab2.program.main.MainProgram
import ru.ifmo.st.lab2.sl.buildContainer

suspend fun main() = ProgramFramework(MainProgram(), container = appContainer)
        .run()
