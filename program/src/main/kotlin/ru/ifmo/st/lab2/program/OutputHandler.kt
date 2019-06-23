package ru.ifmo.st.lab2.program

abstract class OutputHandler {
    abstract fun showMessage(value: String)

    internal abstract fun printPointer(pointer: String)
}
