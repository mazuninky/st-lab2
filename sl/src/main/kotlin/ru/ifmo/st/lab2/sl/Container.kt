package ru.ifmo.st.lab2.sl

import java.util.concurrent.ConcurrentNavigableMap
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class Container(vararg modules: Container) : InjectableContainer {

    private val deps = mutableMapOf<KClass<*>, Dependency>()

    fun set(clazz: KClass<*>, dependency: Dependency) {
        deps[clazz] = dependency
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(clazz: KClass<T>): T {
        if (!deps.containsKey(clazz))
            throw CantFindDependencyException(clazz)

        val dep = deps[clazz] as Dependency
        return when (dep) {
            is SingleDependency<*> -> dep.instance
            is FactoryDependency<*> -> dep.factory(this)
        } as T
    }

    inline fun <reified T> single(instance: T) {
        set(T::class, SingleDependency(instance))
    }

    inline fun <reified T> single(crossinline factory: Container.() -> T) {
        set(T::class, SingleDependency(factory()))
    }

    /**
     * Невозможность использовать с конструктором с аргументами, а также с не публичными конструкторами
     */
    inline fun <reified T : Any> single() {
        set(T::class, SingleDependency(T::class.createInstance()))
    }

    inline fun <reified T> factory(noinline factory: Container.() -> T) {
        set(T::class, FactoryDependency(factory))
    }

    fun add(container: Container) {
        container.deps.forEach { (key, dep) -> deps[key] = dep }
    }

    init {
        modules.forEach { add(it) }
    }
}

inline fun buildContainer(body: Container.() -> Unit): Container {
    return Container().apply(body)
}

inline fun buildContainer(vararg modules: Container, body: Container.() -> Unit = {}): Container {
    return Container(*modules).apply(body)
}


val emptyContainer = Container()
