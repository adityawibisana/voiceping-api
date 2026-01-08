package io.github.kotlin.fibonacci

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.POST
import io.github.kotlin.fibonacci.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val clientId = "2359media"
const val clientSecret = "2359admin"
const val grantType = "password"

interface VoicepingApi {
    @POST("v2/oauth/token")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = io.github.kotlin.fibonacci.grantType,
        @Field("client_id") clientId: String = io.github.kotlin.fibonacci.clientId,
        @Field("client_secret") clientSecret: String = io.github.kotlin.fibonacci.clientSecret,
    ): LoginResponse

}

class API {
    private val baseUrl = "https://staging.voiceoverping.net/"

    private val jsonConfig = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(jsonConfig)
        }
    }

    private val ktorfit = Ktorfit.Builder()
        .baseUrl(baseUrl)
        .httpClient(httpClient)
        .build()

    val service: VoicepingApi = ktorfit.createVoicepingApi()
}