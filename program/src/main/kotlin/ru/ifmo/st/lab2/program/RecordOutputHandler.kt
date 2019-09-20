package ru.ifmo.st.lab2.program

import java.io.OutputStream

class RecordOutputHandler : OutputHandler() {
    private val records = mutableListOf<String>()

    override fun showMessage(value: String) {
        records.add(value)
    }

    override fun printPointer(pointer: String) {

    }

    val message: List<String> = records
}
