package ru.ifmo.st.lab2.data

import ru.ifmo.st.lab2.gateway.IOGateway
import java.io.File

class DiskIOGateway : IOGateway {
    override fun writeToFile(fileName: String, data: String) {
        File(fileName).bufferedWriter().use { it.write(data) }
    }

    override fun readFromFile(fileName: String): String =
        File(fileName).readText()

    override fun checkFileExist(fileName: String): Boolean = File(fileName).exists()
}
