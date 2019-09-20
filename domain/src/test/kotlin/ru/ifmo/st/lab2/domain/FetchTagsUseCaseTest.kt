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

class FetchTagsUseCaseTest {
    private lateinit var dbGateway: TaskDBGateway
    private lateinit var fetch: FetchTagsUseCase

    private val tags = listOf(
        "lol", "lel", "kek"
    )

    @BeforeEach
    fun init() {
        dbGateway = mock {
            on { fetchTags() } doReturn tags
        }
        fetch = FetchTagsUseCaseImpl(dbGateway)
    }

    @Test
    fun `when invoke UseCase`() {
        val actual = fetch()
        verify(dbGateway).fetchTags()
        Assertions.assertEquals(tags, actual)
    }
}
