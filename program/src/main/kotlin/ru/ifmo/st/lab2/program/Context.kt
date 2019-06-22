package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.sl.Container
import ru.ifmo.st.lab2.sl.InjectableContainer
import kotlin.reflect.full.createInstance

class Context(val container: InjectableContainer) {
    private val data = mutableMapOf<String, Any>()

    fun <T> setValue(key: String, value: Any) {
        data[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(key: String): T {
        return data[key] as T
    }

    private val requests: MutableList<Request> = mutableListOf()

    fun startProgram(program: Program) {
        requests.add(StartProgramRequest(program))
    }

    inline fun <reified T : Program> startProgram() {
        startProgram(T::class.createInstance())
    }

    internal inline fun processRequest(handler: (Request) -> Unit) {
        requests.forEach(handler)
        requests.clear()
    }
}
