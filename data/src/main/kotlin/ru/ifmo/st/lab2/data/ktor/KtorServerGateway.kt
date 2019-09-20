package ru.ifmo.st.lab2.data.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.auth.providers.basic
import io.ktor.client.request.get
import io.ktor.client.response.HttpResponse
import io.ktor.http.HttpStatusCode
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
}
