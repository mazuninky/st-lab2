package ru.ifmo.st.lab2.module

import ru.ifmo.st.lab2.domain.*
import ru.ifmo.st.lab2.sl.buildContainer
import ru.ifmo.st.lab2.sl.get

val domainModule = buildContainer {
    factory<AddNewTaskUseCase> { AddNewTaskUseCaseImpl(get()) }

    factory<FetchNActualTaskUseCase> { FetchNActualTaskUseCaseImpl(get()) }
    factory<FetchActualTaskUseCase> { FetchActualTaskUseCaseImpl(get()) }

    factory<FetchTaskUseCase> { FetchTaskUseCaseImpl(get()) }
    factory<FetchNTaskUseCase> { FetchNTaskUseCaseImpl(get()) }

    factory<ExportDBUseCase> { ExportDBUseCaseImpl(get(), get(), get()) }
    factory<ImportDBUseCase> { ImportDBUseCaseImpl(get(), get()) }

    factory<FindTaskByIdOrNameUseCase> { FindTaskByIdOrNameUseCaseImpl(get()) }
    factory<UpdateTaskUseCase> { UpdateTaskUseCaseImpl(get()) }
    factory<DeleteTaskUseCase> { DeleteTaskUseCaseImpl(get()) }
}
