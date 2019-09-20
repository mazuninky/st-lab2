package ru.ifmo.st.lab2.sl

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.jvmErasure

interface InjectableContainer {
    fun <T : Any> get(clazz: KClass<T>): T
}

inline fun <reified T : Any> InjectableContainer.get(): T {
    return get(T::class)
}


inline fun <reified T : Any> InjectableContainer.create(): T {
    val constructor = T::class.primaryConstructor!!
    return if (constructor.parameters.isEmpty())
        T::class.createInstance()
    else {
        val args = constructor.valueParameters.map {
            get(it.type.jvmErasure)
        }
        constructor.call(*args.toTypedArray())
    }
}
