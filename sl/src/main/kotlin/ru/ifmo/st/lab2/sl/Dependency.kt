package ru.ifmo.st.lab2.sl

sealed class Dependency

data class SingleDependency<T>(val instance: T) : Dependency()

data class FactoryDependency<T>(val factory: Container.() -> T) : Dependency()
