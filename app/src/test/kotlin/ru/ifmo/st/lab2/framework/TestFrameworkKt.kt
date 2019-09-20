package ru.ifmo.st.lab2.framework

import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Assertions
import ru.ifmo.st.lab2.program.*
import ru.ifmo.st.lab2.sl.*

class ProgramTest(
    private val programFramework: ProgramFramework,
    private val recordOutputHandler: RecordOutputHandler,
    private val expected: List<String>,
    private val debug: Boolean
) {
    fun test() {
        programFramework.run()
        if (debug) {
            printDebug()
        } else
            Assertions.assertEquals(expected, recordOutputHandler.message)
    }

    fun printDebug() {
        print("Status: ".blueColored())
        println(if (expected == recordOutputHandler.message) "OK".greenColored() else "FAIL".redColored())
        println("Expected:".blueColored())
        expected.forEach(::println)
        println("Actual:".blueColored())
        recordOutputHandler.message.forEach(::println)
    }
}

class EnvBody {
    private val mutableMap: MutableMap<String, Any> = mutableMapOf()

    fun set(key: String, value: Any) {
        mutableMap[key] = value
    }

    fun set(key: String, body: () -> Any) {
        mutableMap[key] = body()
    }

    val map: Map<String, Any> = mutableMap
}

class StoryBody {
    private val input = mutableListOf<String>()
    private val output = mutableListOf<String>()

    fun input(item: String) {
        input.add(item)
    }

    fun input(vararg item: String) {
        input.add(item.joinToString(" "))
    }

    fun input(body: () -> String) {
        input.add(body())
    }

    fun output(item: String) {
        output.add(item)
    }

    fun output(body: () -> String) {
        output.add(body())
    }

    val inputLines: List<String> = input
    val outputLines: List<String> = output
}

class ConstructListBody {
    private val list = mutableListOf<String>()

    fun single(item: String) {
        list.add(item)
    }

    fun single(body: () -> String) {
        list.add(body())
    }

    fun many(vararg items: String) {
        list.addAll(items)
    }

    fun many(itemList: List<String>) {
        list.addAll(itemList)
    }

    fun many(body: () -> List<String>) {
        list.addAll(body())
    }

    fun multiline(item: String) {
        list.addAll(item.split("\n"))
    }

    fun multiline(body: () -> String) {
        list.addAll(body().split("\n"))
    }

    val lines: List<String> = list
}

data class ProgramTestContext(
    var testContainer: Container = emptyContainer,
    var input: List<String> = emptyList(),
    var programInit: (InjectableContainer) -> Program = { throw NotImplementedError(); },
    var args: List<String> = emptyList(),
    var output: List<String> = emptyList(),
    var env: Map<String, Any> = emptyMap(),
    var isDebug: Boolean = false
) {
    inline fun container(body: Container.() -> Unit) {
        testContainer = buildContainer(body)
    }

    fun program(body: (InjectableContainer) -> Program) {
        programInit = body
    }

    fun env(body: EnvBody.() -> Unit) {
        val envBody = EnvBody().apply(body)
        env = envBody.map
    }

    fun debug() {
        isDebug = true
    }

    fun args(vararg argList: String) {
        args = argList.toList()
    }

    fun story(body: StoryBody.() -> Unit) {
        val story = StoryBody().apply(body)
        input = story.inputLines
        output = story.outputLines
    }

    inline fun constructArgs(body: ConstructListBody.() -> Unit) {
        val construct = ConstructListBody().apply(body)
        args = construct.lines
    }

    inline fun constructOutput(body: ConstructListBody.() -> Unit) {
        val construct = ConstructListBody().apply(body)
        output = construct.lines
    }

    inline fun constructInput(body: ConstructListBody.() -> Unit) {
        val construct = ConstructListBody().apply(body)
        input = construct.lines
    }

    fun build(): ProgramTest {
        val recordHandler = RecordOutputHandler()
        val inputStream = "system_input\n".plus(input.joinToString("\n")).byteInputStream()
        val testApp = ProgramFramework(
            StartProgramWithArgs(args, env, programInit),
            container = testContainer,
            output = recordHandler,
            input = inputStream
        )

        return ProgramTest(testApp, recordHandler, output, isDebug)
    }
}

inline fun buildUITest(body: ProgramTestContext.() -> Unit): ProgramTest {
    return ProgramTestContext().apply(body).build()
}

inline fun runUITest(body: ProgramTestContext.() -> Unit) {
    buildUITest(body).test()
}

// Mock

inline fun <reified T : Any> Container.mock() {
    single { mock<T> {} }
}

// Output colors

private const val redColor: String = "\u001B[31m"
private const val blueColor: String = "\u001B[34m"
private const val greenColor: String = "\u001B[32m"
private const val resetColor: String = "\u001B[0m"

fun String.colored(colorSym: String): String {
    return "$colorSym$this$resetColor"
}

fun String.redColored() = colored(redColor)
fun String.greenColored() = colored(greenColor)
fun String.blueColored() = colored(blueColor)
