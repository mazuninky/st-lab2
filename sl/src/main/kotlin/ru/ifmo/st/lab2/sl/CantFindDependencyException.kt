package ru.ifmo.st.lab2.sl

import kotlin.reflect.KClass

class CantFindDependencyException(val expected: KClass<*>) : Exception() {
    override val message: String?
        get() = "can't find $expected"
}
