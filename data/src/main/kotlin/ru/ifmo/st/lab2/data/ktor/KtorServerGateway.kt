package ru.ifmo.st.lab2.data.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.auth.providers.basic
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse
import io.ktor.content.ByteArrayContent
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import ru.ifmo.st.lab2.core.Credentials
import ru.ifmo.st.lab2.core.Task
import ru.ifmo.st.lab2.data.serialization.TaskDTO
import ru.ifmo.st.lab2.data.serialization.toDTO
import ru.ifmo.st.lab2.domains.SyncType
import ru.ifmo.st.lab2.gateway.ServerGateway

class KtorServerGateway : ServerGateway {

    override suspend fun login(login: String, pass: String): Boolean {
        val client = HttpClient(Apache) {
            install(Auth) {
                basic {
                    username = login
                    password = pass
                }
            }
        }
        try {
            val loginResp = client.get<HttpResponse>("http://localhost:8080/user")
            return loginResp.status == HttpStatusCode.OK
        } catch (e: Exception) {

        } finally {
            client.close()
        }

        return false
    }

    override suspend fun registration(login: String, pass: String): Boolean {
        val client = HttpClient(Apache)
        try {
            val userDTO = UserCredDTO(login, pass)
            val json = Json(JsonConfiguration.Stable)
            val loginResp = client.post<HttpResponse> {
                url("http://localhost:8080/registration")
                body = TextContent(json.stringify(UserCredDTO.serializer(), userDTO), ContentType.Text.Plain)
            }
            return loginResp.status == HttpStatusCode.OK
        } catch (e: Exception) {

        } finally {
            client.close()
        }

        return false
    }

    override suspend fun loadToServer(credentials: Credentials, dataBlob: String): Boolean {
        val client = HttpClient(Apache) {
            install(Auth) {
                basic {
                    username = credentials.username
                    password = credentials.password
                }
            }
        }
        try {
            val loginResp = client.post<HttpResponse>() {
                url("http://localhost:8080/sync")
                body = ByteArrayContent(dataBlob.toByteArray(Charsets.UTF_8))
            }
            return loginResp.status == HttpStatusCode.OK
        } catch (e: Exception) {

        } finally {
            client.close()
        }

        return false
    }

    override suspend fun loadFromServer(credentials: Credentials): String? {
        val client = HttpClient(Apache) {
            install(Auth) {
                basic {
                    username = credentials.username
                    password = credentials.password
                }
            }
        }
        try {
            val loginResp = client.get<HttpResponse>() {
                url("http://localhost:8080/sync")
            }
            return if (loginResp.status == HttpStatusCode.OK) loginResp.receive<ByteArray>().toString(Charsets.UTF_8) else null
        } catch (e: Exception) {

        } finally {
            client.close()
        }

        return null
    }
}

@Serializable
data class UserCredDTO(val username: String, val password: String)
