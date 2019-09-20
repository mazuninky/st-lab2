package ru.ifmo.st.lab2.domains

interface SyncServerUseCase {
    operator fun invoke(syncType: SyncType): Boolean
}

enum class SyncType {
    Upload, Download
}
