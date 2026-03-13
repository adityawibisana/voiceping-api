package aditya.wibisana.voicepingapi

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSURLCredential
import platform.Foundation.NSURLSessionAuthChallengeCancelAuthenticationChallenge
import platform.Foundation.NSURLSessionAuthChallengeUseCredential
import platform.Foundation.setHTTPShouldUsePipelining

actual fun getTestEngine(): HttpClientEngine? {
    return Darwin.create {
        configureRequest {
            setAllowsCellularAccess(true)
            setTimeoutInterval(60.0)
            setHTTPShouldUsePipelining(false)
        }
        handleChallenge { _, _, challenge, completionHandler ->
            val serverTrust = challenge.protectionSpace.serverTrust
            if (serverTrust != null) {
                completionHandler(
                    NSURLSessionAuthChallengeUseCredential,
                    NSURLCredential.credentialForTrust(serverTrust)
                )
            } else {
                completionHandler(NSURLSessionAuthChallengeCancelAuthenticationChallenge, null)
            }
        }
    }
}