package ru.ifmo.st.lab2.sl

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.gherkin.Feature

class SimpleDependency

class SimpleClass(val dependency: SimpleDependency)


object ContainerSpek : Spek({
    val container by memoized(mode = CachingMode.GROUP) {
        buildContainer {
            single<SimpleDependency>()
            factory { SimpleClass(get()) }
        }
    }
    Feature("Inject dependency from container") {
        Scenario("Inject single dependency") {
            lateinit var singleInstance: SimpleDependency
            When("When inject SimpleDependency from container") {
                singleInstance = container.get()
            }

            Then("It should be typeof SimpleDependency") {
                singleInstance shouldBeInstanceOf SimpleDependency::class
            }

            lateinit var secondSingleInstance: SimpleDependency
            When("When inject SimpleDependency from container in the second time") {
                secondSingleInstance = container.get()
            }

            Then("It should be typeof SimpleDependency") {
                secondSingleInstance shouldBeInstanceOf SimpleDependency::class
            }

            Then("It should be different instance of class") {
                (singleInstance === secondSingleInstance) shouldBe true
            }
        }

        Scenario("Inject factory dependency") {
            lateinit var factoryInstance: SimpleClass
            When("When inject SimpleClass from container") {
                factoryInstance = container.get()
            }

            Then("It should be typeof SimpleClass") {
                factoryInstance shouldBeInstanceOf SimpleClass::class
            }

            lateinit var secondFactoryInstance: SimpleClass
            When("When inject SimpleClass from container in the second time") {
                secondFactoryInstance = container.get()
            }

            Then("It should be typeof SimpleClass") {
                secondFactoryInstance shouldBeInstanceOf SimpleClass::class
            }

            Then("It should be different instance of class") {
                (factoryInstance !== secondFactoryInstance) shouldBe true
            }
        }
    }
})
