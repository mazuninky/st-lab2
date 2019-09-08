package ru.ifmo.st.lab2.program

sealed class Request

data class StartProgramRequest(val program: Program) : Request()

sealed class ContextRequest : Request()

data class SetToContext(val key: String, val value: Any) : ContextRequest()
data class ClearContextKey(val key: String) : ContextRequest()
