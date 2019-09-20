package ru.ifmo.st.lab2.module

import ru.ifmo.st.lab2.domain.*
import ru.ifmo.st.lab2.domain.fetch.*
import ru.ifmo.st.lab2.domains.SyncServerUseCase
import ru.ifmo.st.lab2.sl.buildContainer
import ru.ifmo.st.lab2.sl.get

val domainModule = buildContainer {
    factory<AddNewTaskUseCase> { AddNewTaskUseCaseImpl(get()) }

    factory<FetchNActualTaskUseCase> { FetchNActualTaskUseCaseImpl(get()) }
    factory<FetchActualTaskUseCase> { FetchActualTaskUseCaseImpl(get()) }

    factory<FetchTaskUseCase> { FetchTaskUseCaseImpl(get()) }
    factory<FetchNTaskUseCase> { FetchNTaskUseCaseImpl(get()) }

    factory<ExportDBUseCase> { ExportDBUseCaseImpl(get(), get(), get()) }
    factory<ImportDBUseCase> { ImportDBUseCaseImpl(get(), get(), get()) }

    factory<FindTaskByIdOrNameUseCase> { FindTaskByIdOrNameUseCaseImpl(get()) }
    factory<UpdateTaskUseCase> { UpdateTaskUseCaseImpl(get()) }
    factory<DeleteTaskUseCase> { DeleteTaskUseCaseImpl(get()) }
    factory<FetchTagsUseCase> { FetchTagsUseCaseImpl(get()) }
    factory<SearchTasksByTagsUseCase> { SearchTasksByTagsUseCaseImpl(get()) }
    factory<DoneTaskUseCase> { DoneTaskUseCaseImpl(get()) }
    factory<FetchOverdueTaskUseCase> { FetchOverdueTaskUseCaseImpl(get()) }
    factory<LoginUseCase> { LoginUseCaseImpl(get(), get()) }
    factory<FetchUserInfoUseCase> { FetchUserInfoUseCaseImpl(get()) }
    factory<RegistrationUseCase> { RegistrationUseCaseImpl(get()) }
    factory<SyncServerUseCase> { SyncServerUseCaseImpl(get(), get(), get(), get()) }
}
