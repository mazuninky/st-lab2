package ru.ifmo.st.lab2.program

import java.io.OutputStream

object PrintHandler : OutputHandler() {
    override fun showMessage(value: String) {
        println(value)
    }

    override fun printPointer(pointer: String) {
        print(pointer)
    }
}
