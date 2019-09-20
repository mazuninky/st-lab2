package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.IOGateway

class CheckFileExistsUseCaseImpl(private val ioGateway: IOGateway) : CheckFileExistsUseCase {
    override operator fun invoke(fileName: String): Boolean {
        return ioGateway.checkFileExist(fileName)
    }
}
