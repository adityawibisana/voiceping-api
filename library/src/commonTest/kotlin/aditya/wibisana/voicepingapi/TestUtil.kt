package aditya.wibisana.voicepingapi

import io.ktor.client.engine.HttpClientEngine

// Defines the expectation: Each platform must provide a test engine (or null)
expect fun getTestEngine(): HttpClientEngine?