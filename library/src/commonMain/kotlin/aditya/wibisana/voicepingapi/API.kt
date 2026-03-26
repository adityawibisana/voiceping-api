package aditya.wibisana.voicepingapi

import aditya.wibisana.voicepingapi.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val clientId = "2359media"
const val clientSecret = "2359admin"
const val grantType = "password"

interface VoicepingApi {
    suspend fun login(
        username: String,
        password: String,
        grantType: String = aditya.wibisana.voicepingapi.grantType,
        clientId: String = aditya.wibisana.voicepingapi.clientId,
        clientSecret: String = aditya.wibisana.voicepingapi.clientSecret,
    ): LoginResponse.Success
}

class API(
    engine: HttpClientEngine? = null,
    baseUrl: String = "https://staging.voiceoverping.net/"
) {
    private val jsonConfig = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    private val httpClient = if (engine == null) {
        HttpClient {
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }
    } else {
        HttpClient(engine) {
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }
    }

    val service: VoicepingApi = object : VoicepingApi {
        override suspend fun login(
            username: String,
            password: String,
            grantType: String,
            clientId: String,
            clientSecret: String,
        ): LoginResponse.Success = httpClient.submitForm(
            url = "${baseUrl}v2/oauth/token",
            formParameters = Parameters.build {
                append("username", username)
                append("password", password)
                append("grant_type", grantType)
                append("client_id", clientId)
                append("client_secret", clientSecret)
            }
        ).body()
    }
}
