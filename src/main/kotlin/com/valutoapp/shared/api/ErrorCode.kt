package com.valutoapp.shared.api

enum class ErrorCode {
    // Shared errors
    INVALID_BODY,
    VALIDATION_FAILED,
    INTERNAL_ERROR,

    // User errors
    EMAIL_TAKEN,
}
