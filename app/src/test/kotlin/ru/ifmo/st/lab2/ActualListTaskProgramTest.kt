package ru.ifmo.st.lab2

import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import ru.ifmo.st.lab2.domain.FetchActualTaskUseCase
import ru.ifmo.st.lab2.domain.FetchNActualTaskUseCase
import ru.ifmo.st.lab2.program.ActualListTaskProgram
import ru.ifmo.st.lab2.program.ProgramFramework

class ActualListTaskProgramTest {

    @Test
    fun test() {
        val fetchActualTasks: FetchActualTaskUseCase = mock { }
        val fetchNActualTasks: FetchNActualTaskUseCase = mock { }
        val testApp = ProgramFramework(ActualListTaskProgram(fetchActualTasks, fetchNActualTasks))
    }
}
