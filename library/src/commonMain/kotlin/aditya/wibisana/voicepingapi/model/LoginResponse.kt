package aditya.wibisana.voicepingapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class LoginResponse {
    @Serializable
    data class Success(
        @SerialName("access_token")
        val accessToken: String,

        @SerialName("socket_url")
        val socketUrl: String,

        @SerialName("avatar_url")
        val avatarUrl: String,

        @SerialName("token_type")
        val tokenType: String,

        val username: String,

        @Suppress("spellCheckingInspection") @SerialName("fullname")
        val displayName: String,
        val uuid: String,
        val privilege: String,
        val email: String,
        val company: String,
        val phone: String?,
        val id: Int,
        val status: String,
        val statusValue: Int,
    )

    @Serializable
    data class Failed(
        val code: Int? = null,
        val message: String? = null
    )
}