package ru.ifmo.st.lab2.gateway

interface IOGateway {
    fun writeToFile(fileName: String, data: String)
    fun readFromFile(fileName: String): String
    fun checkFileExist(fileName: String): Boolean
}
