package ru.ifmo.st.lab2.backend

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*
import io.ktor.request.receive
import io.ktor.request.receiveOrNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import kotlinx.serialization.parse

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

val creds = mutableMapOf(
    "username" to "password"
)

val dataTable = mutableMapOf<String, String>()

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

            post("sync") {
                val name = requireNotNull(call.principal<UserIdPrincipal>()?.name)
                dataTable[name] = call.receive()
                call.respondText("Success", contentType = ContentType.Text.Plain)
            }

            get("sync") {
                val name = requireNotNull(call.principal<UserIdPrincipal>()?.name)
                call.respondText("${dataTable[name]}", contentType = ContentType.Text.Plain)
            }
        }

        post("registration") {
            val body = call.receiveOrNull<String>()

            if (body != null) {
                val json = Json(JsonConfiguration.Stable)
                val cred = json.parse(UserCredDTO.serializer(), body)
                if (creds.containsKey(cred.username)) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                creds[cred.username] = cred.password
                call.respondText("OK")
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

@Serializable
data class UserCredDTO(val username: String, val password: String)

