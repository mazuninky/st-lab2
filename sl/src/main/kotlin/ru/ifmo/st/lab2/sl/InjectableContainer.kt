package ru.ifmo.st.lab2.sl

import kotlin.reflect.KClass

interface InjectableContainer {
    fun <T : Any> get(clazz: KClass<T>): T
}

inline fun <reified T : Any> InjectableContainer.get(): T {
    return get(T::class)
}
