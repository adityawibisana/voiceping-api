package aditya.wibisana.voicepingapi

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LoginTest {
    val api: API = API()

    @Test
    fun login() = runTest {
        val response = api.service.login(
            username = "1@trial.vp",
            password = "thanksfortesting",
        )
        assertEquals("1@trial.vp", response.username)
        assertTrue(response.accessToken.isNotEmpty())
        assertEquals("trial.vp", response.company)
    }
}