package io.github.kotlin.fibonacci.aditya.wibisana.voicepingapi.repository

import aditya.wibisana.voicepingapi.VoicepingApi
import aditya.wibisana.voicepingapi.model.LoginResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Login(
    private val apiService: VoicepingApi
){
    operator fun invoke(
        username: String,
        password: String
    ) : Flow<State> = flow {
        emit(State.Loading)
        try {
            emit(State.Success(apiService.login(username = username, password = password)))
        } catch (e: ResponseException) {
            val failed = try {
                e.response.body<LoginResponse.Failed>()
            } catch (_: Exception) {
                LoginResponse.Failed(code = e.response.status.value, message = e.message)
            }
            emit(State.Failed(failed))
        } catch (e: Exception) {
            emit(State.Failed(LoginResponse.Failed(code = 500, message = e.message)))
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val data: LoginResponse.Success) : State()
        data class Failed(val data: LoginResponse.Failed) : State()
    }
}
