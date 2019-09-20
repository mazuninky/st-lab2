package ru.ifmo.st.lab2.program

import ru.ifmo.st.lab2.sl.Container
import ru.ifmo.st.lab2.sl.InjectableContainer
import ru.ifmo.st.lab2.sl.create
import ru.ifmo.st.lab2.sl.get
import kotlin.reflect.KParameter
import kotlin.reflect.full.cast
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.jvmErasure

class Context(val container: InjectableContainer) {
    private val data = mutableMapOf<String, Any>()

    fun setValue(key: String, value: Any) {
        data[key] = value
    }

    fun setMap(map: Map<String, Any>) {
        map.forEach(this::setValue)
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
        startProgram(container.create<T>())
    }


}
