package com.valutoapp.user.api

import com.valutoapp.shared.api.ApiError
import com.valutoapp.shared.api.ErrorCode
import io.ktor.http.HttpStatusCode

object UserErrors {
    fun emailTaken(email: String) = ApiError(
        HttpStatusCode.Conflict,
        ErrorCode.EMAIL_TAKEN,
        "User with email $email already exists",
    )
}
