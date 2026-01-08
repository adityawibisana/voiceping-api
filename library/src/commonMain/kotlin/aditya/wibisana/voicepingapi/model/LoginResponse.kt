package aditya.wibisana.voicepingapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("access_token")
    val accessToken: String,

    val username: String,

    @Suppress("spellCheckingInspection") @SerialName("fullname")
    val displayName: String,
    val uuid: String,
    val privilege: String,

    @SerialName("avatar_url")
    val avatarUrl: String,

    val email: String,
    val company: String,

    // Explicitly null in your JSON, so must be nullable here
    val phone: String?,

    val id: Int,

    @SerialName("socket_url")
    val socketUrl: String,

    val status: String,
    val statusValue: Int,

    @SerialName("token_type")
    val tokenType: String
)