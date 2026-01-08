package aditya.wibisana.voicepingapi

import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LoginTest {
    val api: API = API()

    @Test
    fun login() = runTest {
        val response = api.service.login(
            username = "1@trial.vp",
            password = "thanksfortesting",
        )
    }
}