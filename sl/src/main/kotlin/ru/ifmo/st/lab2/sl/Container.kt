package ru.ifmo.st.lab2.sl

import kotlin.reflect.KClass

sealed class Dependency

data class SingleDependency<T>(val instance: T) : Dependency()

data class FactoryDependency<T>(val factory: (Container) -> T) : Dependency()

class Container {
    private val deps = mutableMapOf<KClass<*>, Dependency>()

    fun set(clazz: KClass<*>, dependency: Dependency) {
        deps[clazz] = dependency
    }

    fun <T : Any> get(clazz: KClass<T>): T {
        if (!deps.containsKey(clazz))
            throw CantFindDependencyException()

        val dep = deps[clazz] as Dependency
        return when (dep) {
            is SingleDependency<*> -> dep.instance
            is FactoryDependency<*> -> dep.factory(this)
        } as T
    }

    inline fun <reified T : Any> get(): T {
        return get(T::class)
    }

    inline fun <reified T> single(instanse: T) {
        set(T::class, SingleDependency(instanse))
    }

    inline fun <reified T> factory(noinline factory: (Container) -> T) {
        set(T::class, FactoryDependency(factory))
    }
}

inline fun buildContainer(body: Container.() -> Unit): Container {
    return Container().apply(body)
}