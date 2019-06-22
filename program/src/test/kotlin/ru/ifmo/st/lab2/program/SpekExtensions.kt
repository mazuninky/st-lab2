package ru.ifmo.st.lab2.program

import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.dsl.TestBody
import org.spekframework.spek2.style.gherkin.ScenarioBody

fun ScenarioBody.cWhen(description: String, body: suspend TestBody.() -> Unit) {
    When(description) {
        runBlocking {
            body()
        }
    }
}

fun ScenarioBody.cGiven(description: String, body: suspend TestBody.() -> Unit) {
    Given(description) {
        runBlocking {
            body()
        }
    }
}

fun ScenarioBody.cThen(description: String, body: suspend TestBody.() -> Unit) {
    Then(description) {
        runBlocking {
            body()
        }
    }
}
