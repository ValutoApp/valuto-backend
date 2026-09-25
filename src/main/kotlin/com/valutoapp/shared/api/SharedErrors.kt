package com.valutoapp.shared.api

import io.ktor.http.HttpStatusCode

object SharedErrors {
    fun validationFailed(violations: List<FieldViolation>) = ApiError(
        status = HttpStatusCode.UnprocessableEntity,
        code = ErrorCode.VALIDATION_FAILED,
        message = "Invalid input data",
        violations,
    )

    fun validationFailed(vararg fieldWithMessage: Pair<String, String?>): ApiError = validationFailed(
        fieldWithMessage.mapNotNull { (field, message) -> message?.let { FieldViolation(field, message) } },
    )

    fun invalidBody() = ApiError(
        status = HttpStatusCode.BadRequest,
        code = ErrorCode.INVALID_BODY,
        message = "Invalid or missing request body",
    )

    fun internalError() = ApiError(
        status = HttpStatusCode.InternalServerError,
        code = ErrorCode.INTERNAL_ERROR,
        message = "Something went wrong, try again later",
    )
}
