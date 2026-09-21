package com.valutoapp.shared.api

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: ErrorCode,
    val message: String,
    val traceId: String,
    val violations: List<FieldViolation> = emptyList(),
)
