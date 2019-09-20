package ru.ifmo.st.lab2.domain

import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.gateway.TaskDBGateway

class FindTaskByIdOrNameUseCaseImpl(private val dbGateway: TaskDBGateway) : FindTaskByIdOrNameUseCase {
    override fun invoke(criterion: String): Task? {
        check(criterion.isNotBlank())
        return dbGateway.findTaskByIdOrName(criterion)
    }
}
