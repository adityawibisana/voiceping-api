package aditya.wibisana.voicepingapi

import io.github.kotlin.fibonacci.aditya.wibisana.voicepingapi.repository.Login
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class LoginTest {
    val api: API = API()

    @Test
    fun `should be able to login with correct username and password`() = runTest {
        val loginRepository = Login(api.service)
        val emissions = loginRepository.invoke(
            username = "1@trial.vp",
            password = "thanksfortesting",
        ).toList()

        assertTrue { emissions.size == 2 }
        assertTrue { emissions[0] is Login.State.Loading }
        assertTrue { emissions[1] is Login.State.Success }

        val data = (emissions[1] as Login.State.Success).data
        assertTrue(data.accessToken.isNotEmpty())
        assertTrue { data.username == "1@trial.vp" }
        assertTrue { data.company == "trial.vp" }
    }

    @Test
    fun `should return error when username or password is mismatched`() = runTest {
        val loginRepository = Login(api.service)
        val emissions = loginRepository.invoke(
            username = "1@trial.vp",
            password = "7zf5QV0lhyyZY0BePGpEgOFJXprT8Hsv", // just random password
        ).toList()

        assertTrue { emissions.size == 2 }
        assertTrue { emissions[0] is Login.State.Loading }
        assertTrue { emissions[1] is Login.State.Failed }

        val data = (emissions[1] as Login.State.Failed).data
        assertTrue(data.message?.isNotEmpty() == true)
    }
}