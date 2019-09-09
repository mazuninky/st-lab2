package ru.ifmo.st.lab2.gateway

interface IOGateway {
    fun writeToFile(fileName: String, data: String)
}
