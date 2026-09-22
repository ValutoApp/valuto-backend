package com.valutoapp.user.api

import com.valutoapp.shared.api.FieldViolation
import com.valutoapp.shared.api.SharedErrors
import com.valutoapp.shared.api.respondError
import com.valutoapp.user.application.RegisterUserResult
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

suspend fun ApplicationCall.respond(result: RegisterUserResult) {
    when (result) {
        is RegisterUserResult.Success -> respond(HttpStatusCode.Created, RegisterUserResponse(result.userId.toString()))
        is RegisterUserResult.EmailTaken -> respondError(UserErrors.emailTaken(result.email.value))
        is RegisterUserResult.InvalidInput -> respondError(result.toValidationError())
    }
}

private fun RegisterUserResult.InvalidInput.toValidationError() = SharedErrors.validationFailed(
    listOfNotNull(
        email.errorMessage()?.let { FieldViolation(RegisterUserRequest::email.name, it) },
        password.errorMessage()?.let { FieldViolation(RegisterUserRequest::password.name, it) },
    ),
)
