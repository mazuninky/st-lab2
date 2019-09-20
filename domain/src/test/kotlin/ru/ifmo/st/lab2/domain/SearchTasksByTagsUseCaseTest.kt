package ru.ifmo.st.lab2.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.domain.fetch.FetchActualTaskUseCaseImpl
import ru.ifmo.st.lab2.domain.fetch.FetchNActualTaskUseCaseImpl
import ru.ifmo.st.lab2.domain.fetch.FetchTaskUseCaseImpl
import ru.ifmo.st.lab2.gateway.TaskDBGateway

class SearchTasksByTagsUseCaseTest {
    private lateinit var dbGateway: TaskDBGateway
    private lateinit var search: SearchTasksByTagsUseCase

    private val tasks = listOf(
        makeSampleTask(tags = listOf("kek")),
        makeSampleTask(tags = listOf("lol", "docker"))
    )

    private val tags = listOf(
        "lol", "lel", "kek"
    )

    @BeforeEach
    fun init() {
        dbGateway = mock {
            on { findTasksByTags(tags) } doReturn tasks
        }
        search = SearchTasksByTagsUseCaseImpl(dbGateway)
    }

    @Test
    fun `when invoke UseCase`() {
        val actual = search(tags)
        verify(dbGateway).findTasksByTags(tags)
        Assertions.assertEquals(tasks, actual)
    }

    @Test
    fun `when invoke UseCase with empty list`() {
        val actual = search(emptyList())
        Assertions.assertEquals(emptyList<Task>(), actual)
    }
}
