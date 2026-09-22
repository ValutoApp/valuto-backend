package com.valutoapp.shared.api

import io.ktor.http.HttpStatusCode

data class ApiError(
    val status: HttpStatusCode,
    val code: ErrorCode,
    val message: String,
    val violations: List<FieldViolation> = emptyList(),
) {
    fun toResponse(traceId: String) = ErrorResponse(code, message, traceId, violations)
}
