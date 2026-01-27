package aditya.wibisana.voicepingapi

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.setHTTPShouldUsePipelining

actual fun getTestEngine(): HttpClientEngine? {
    return Darwin.create {
        configureRequest {
            setAllowsCellularAccess(true)
            setTimeoutInterval(60.0)
            setHTTPShouldUsePipelining(false) // Fixes the hang
        }
    }
}