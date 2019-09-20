package ru.ifmo.st.lab2.backend

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

val creds = mutableMapOf(
    "username" to "password"
)

fun Map<String, String>.login(username: String, password: String): Boolean {
    if (containsKey(username)) {
        return getValue(username) == password
    }

    return false
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Authentication) {
        basic("myBasicAuth") {
            realm = "TodoApp"
            validate {
                if (creds.login(it.name, it.password))
                    UserIdPrincipal(it.name) else null
            }
        }
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        authenticate("myBasicAuth") {
            get("user") {
                call.respondText("Success", contentType = ContentType.Text.Plain)
            }
        }
    }
}

