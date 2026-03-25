package aditya.wibisana.voicepingapi

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.interpretCPointer
import platform.Foundation.*
import platform.Security.SecTrustRef

@OptIn(ExperimentalForeignApi::class)
actual fun getTestEngine(): HttpClientEngine? {
    return Darwin.create {
        configureRequest {
            setAllowsCellularAccess(true)
            setTimeoutInterval(60.0)
            setHTTPShouldUsePipelining(false)
        }
        handleChallenge { _, _, challenge, completionHandler ->
            val serverTrustObj = challenge.protectionSpace.valueForKey("serverTrust")
            val serverTrust: SecTrustRef? = serverTrustObj?.let { interpretCPointer(it.objcPtr()) }
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
