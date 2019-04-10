package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.program.Program

sealed class Request

data class StartProgramRequest(val program: Program) : Request()