package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.sl.Container
import ru.ifmo.st.lab2.sl.InjectableContainer
import kotlin.reflect.full.createInstance

class Context(val container: InjectableContainer) {
    private val data = mutableMapOf<String, Any>()

    fun setValue(key: String, value: Any) {
        data[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(key: String): T {
        return data[key] as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getValueOrDefault(key: String, default: T): T {
        return data.getOrDefault(key, default) as T
    }

    fun clearValue(key: String) {
        data.remove(key)
    }

    inline fun <reified T : Any> inject(): T {
        return container.get(T::class)
    }

    private val requests: MutableList<Request> = mutableListOf()

    internal inline fun processRequest(handler: (Request) -> Unit) {
        requests.forEach {
            if (it is ContextRequest)
                handleContextRequest(it)
            else
                handler(it)
        }
        requests.clear()
    }

    private fun handleContextRequest(request: ContextRequest) {
        when (request) {
            is SetToContext -> setValue(request.key, request.value)
            is ClearContextKey -> clearValue(request.key)
        }
    }

    fun startProgram(program: Program) {
        requests.add(StartProgramRequest(program))
    }

    inline fun <reified T : Program> startProgram() {
        startProgram(T::class.createInstance())
    }


}
