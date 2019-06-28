package ru.ifmo.st.lab2.module

import ru.ifmo.st.lab2.domain.AddNewTaskUseCase
import ru.ifmo.st.lab2.domain.AddNewTaskUseCaseImpl
import ru.ifmo.st.lab2.domain.FetchNActualTaskUseCase
import ru.ifmo.st.lab2.domain.FetchNActualTaskUseCaseImpl
import ru.ifmo.st.lab2.sl.buildContainer
import ru.ifmo.st.lab2.sl.get

val domainModule = buildContainer {
    factory<AddNewTaskUseCase> { AddNewTaskUseCaseImpl(get()) }
    factory<FetchNActualTaskUseCase> { FetchNActualTaskUseCaseImpl(get()) }
}
