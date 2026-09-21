package com.valutoapp.shared.api

import io.ktor.http.HttpStatusCode

object SharedErrors {
    fun validationFailed(violations: List<FieldViolation>) =
        ApiError(
            status = HttpStatusCode.UnprocessableEntity,
            code = ErrorCode.VALIDATION_FAILED,
            message = "Invalid input data",
            violations,
        )

    fun invalidBody() =
        ApiError(
            status = HttpStatusCode.BadRequest,
            code = ErrorCode.INVALID_BODY,
            message = "Invalid or missing request body",
        )

    fun internalError() =
        ApiError(
            HttpStatusCode.InternalServerError,
            ErrorCode.INTERNAL_ERROR,
            "Something went wrong, try again later",
        )
}
