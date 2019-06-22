package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.program.Program
import ru.ifmo.st.lab2.sl.Container
import ru.ifmo.st.lab2.sl.InjectableContainer
import ru.ifmo.st.lab2.sl.emptyContainer
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.StringBufferInputStream
import java.util.*

class ProgramFramework(startProgram: Program,
                       private val input: InputStream = System.`in`,
                       container: InjectableContainer = emptyContainer) {
    private val programStack = Stack<Program>()

    private var currentProgram = startProgram

    private val context = Context(container)

    suspend fun run() {
        currentProgram.create(context)
        currentProgram.start()

        Scanner(input).use { scanner ->
            var frameworkIsWorking = true
            while (frameworkIsWorking) {
                if (!currentProgram.isWorking) {
                    if (programStack.isNotEmpty()) {
                        currentProgram = programStack.pop()
                        currentProgram.start()
                    } else {
                        frameworkIsWorking = false
                    }
                    continue
                }

                val value = scanner.nextLine()
                currentProgram.process(value)

                context.processRequest {
                    when (it) {
                        is StartProgramRequest -> startProgram(it.program)
                    }
                }
            }
        }
    }

    private fun startProgram(program: Program) {
        programStack.add(currentProgram)
        currentProgram.stop()
        currentProgram = program

        currentProgram.create(context)
        currentProgram.start()
    }
}
