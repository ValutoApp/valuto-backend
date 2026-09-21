package com.valutoapp.app

import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging
import kotlin.uuid.Uuid

fun Application.configureLogging() {
    install(CallId) {
        generate { Uuid.random().toString() }
        header(HttpHeaders.XRequestId)
        verify { callId: String ->
            callId.isNotEmpty()
        }
    }

    install(CallLogging) {
        callIdMdc("traceId")
    }
}
