package com.valutoapp.shared.api

import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.callid.callId
import io.ktor.server.response.respond

suspend fun ApplicationCall.respondError(error: ApiError) {
    val traceId = callId ?: "unknown"
    respond(error.status, error.toResponse(traceId))
}
