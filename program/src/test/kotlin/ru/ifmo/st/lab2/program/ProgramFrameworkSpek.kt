package ru.ifmo.st.lab2.program

import com.nhaarman.mockitokotlin2.*
import org.amshove.kluent.`should be`
import org.amshove.kluent.shouldBe
import org.spekframework.spek2.Spek
import org.spekframework.spek2.dsl.Skip
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.gherkin.Feature
import java.io.*
import kotlin.test.assertEquals

object ProgramFrameworkSpek : Spek({
    Feature("Launch program") {
        lateinit var launchProgramMock: Program

        lateinit var input: InputStream

        val framework by memoized(CachingMode.GROUP) {
            ProgramFramework(launchProgramMock, input)
        }

        val sampleInput = "lorem"
        val sampleInputs = listOf("lorem", "lorem ipsum")

        Scenario("Parse one input and finish work") {
            beforeGroup {
                launchProgramMock = mock {
                    on { isWorking } doReturn true doReturn false
                }
                input = sampleInput.toInputStream()
            }

            When("run program") {
                framework.run()
            }

            Then("program runs with lifecycle events") {
                inOrder(launchProgramMock) {
                    verify(launchProgramMock).create(any())
                    verify(launchProgramMock).start()
                }
            }

            Then("program process sampleInput: $sampleInput") {
                verify(launchProgramMock).process(sampleInput)
            }
        }

        Scenario("Parse two lines and finish work") {
            beforeGroup {
                launchProgramMock = mock {
                    on { isWorking } doReturn true doReturn true doReturn false
                }
                input = sampleInputs.toInputStream()
            }

            When("run program") {
                framework.run()
            }

            Then("program runs with lifecycle events") {
                inOrder(launchProgramMock) {
                    verify(launchProgramMock).create(any())
                    verify(launchProgramMock).start()
                }
            }

            Then("program process sampleInputs: $sampleInputs") {
                argumentCaptor<String>().apply {
                    verify(launchProgramMock, times(2)).process(capture())

                    assertEquals(allValues, sampleInputs)
                }
            }
        }
    }

//    Feature("Start a new program") {
//        val newProgramMock: Program = mock {
//            on { isWorking } doReturn false
//        }
//
//        val launchProgramStub = object : Program {
//            lateinit var context: Context
//
//            override fun create(context: Context) {
//                this.context = context
//            }
//
//            var startCount = 0
//                private set
//
//            override fun start() {
//                startCount++
//            }
//
//            var stopCount = 0
//                private set
//
//            override fun stop() {
//                stopCount++
//            }
//
//            var workingCount = 0
//                private set
//
//            override val isWorking: Boolean =
//                    workingCount++ == 0
//
//            override fun process(input: String) {
//                context.startProgram(newProgramMock)
//            }
//        }
//
//        val input: InputStream = "lorem".toInputStream()
//
//        val framework by memoized(CachingMode.GROUP) {
//            ProgramFramework(launchProgramStub, input)
//        }
//
//        Scenario("Launch a new program") {
//            When("run program") {
//                framework.run()
//            }
//
//            Then("") {
//
//            }
//        }
//    }
})

fun String.toInputStream(): InputStream {
    return ByteArrayInputStream(toByteArray())
}

fun List<String>.toInputStream(): InputStream {
    val builder = StringBuilder()
    forEach { builder.appendln(it) }
    return ByteArrayInputStream(builder.toString().toByteArray())
}