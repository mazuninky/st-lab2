package ru.ifmo.st.lab2.program


interface Program {
    fun create(context: Context, output: OutputHandler)
    fun start()
    fun stop()

    fun process(input: String)

    val isWorking: Boolean
}
