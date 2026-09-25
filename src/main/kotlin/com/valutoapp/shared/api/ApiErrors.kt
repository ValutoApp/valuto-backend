package com.valutoapp.shared.api

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.callid.callId
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable

data class ApiError(
    val status: HttpStatusCode,
    val code: ErrorCode,
    val message: String,
    val violations: List<FieldViolation> = emptyList(),
) {
    fun toResponse(traceId: String) = ErrorResponse(code, message, traceId, violations)
}

@Serializable
data class ErrorResponse(
    val code: ErrorCode,
    val message: String,
    val traceId: String,
    val violations: List<FieldViolation> = emptyList(),
)

@Serializable
data class FieldViolation(
    val field: String,
    val message: String,
)

suspend fun ApplicationCall.respondError(error: ApiError) {
    val traceId = callId ?: "unknown"
    respond(error.status, error.toResponse(traceId))
}
