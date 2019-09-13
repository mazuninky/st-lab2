package ru.ifmo.st.lab2.domain

interface ExportDBUseCase {
     operator fun invoke(fileName: String)
}
