package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.domain.ImportDBUseCase
import ru.ifmo.st.lab2.domain.ImportStrategy
import ru.ifmo.st.lab2.domain.LoginUseCase
import ru.ifmo.st.lab2.program.main.ArgumentCommandProgram

class SyncProgram(
    private val registrate: RegistrationUseCase,
    private val sync: SyncServerUseCase
) : ArgumentCommandProgram() {
    companion object {
        const val AUTH_ERR = "Пользователь не залогинен"
        const val OK = "База синхронизорована"

        const val UPLOAD = "upload"
        const val DOWNLOAD = "download"
        const val SYNC_TYPE_ERR = "Неизвестный тип"
    }

    override fun validateArgs(args: List<String>) = args.size == 1

    override fun afterStart() {
        if (user == null) {
            showMessage(AUTH_ERR)
            return
        }


        val syncType: SyncType
        when (args.first()) {
            UPLOAD -> {
                syncType = SyncType.Upload
            }
            DOWNLOAD -> {
                syncType = SyncType.Download
            }
            else -> {
                showMessage(SYNC_TYPE_ERR)
                return
            }
        }

        sync(syncType)
        showMessage(OK)
    }

}
