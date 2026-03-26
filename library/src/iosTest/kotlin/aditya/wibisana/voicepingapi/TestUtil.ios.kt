package aditya.wibisana.voicepingapi

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.setHTTPShouldUsePipelining

@OptIn(ExperimentalForeignApi::class)
actual fun getTestEngine(): HttpClientEngine? {
    return Darwin.create {
        configureRequest {
            setAllowsCellularAccess(true)
            setTimeoutInterval(60.0)
            setHTTPShouldUsePipelining(false)
        }
    }
}
