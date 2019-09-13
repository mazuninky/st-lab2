package ru.ifmo.st.lab2.domain

interface ImportDBUseCase {
    operator fun invoke(fileName: String, strategy: ImportStrategy)
}

enum class ImportStrategy {
    Add, AcceptOwn, AcceptTheir
}
