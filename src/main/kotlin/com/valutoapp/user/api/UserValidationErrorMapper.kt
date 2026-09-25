package com.valutoapp.user.api

import com.valutoapp.shared.api.ApiError
import com.valutoapp.shared.api.SharedErrors
import com.valutoapp.user.application.RegisterUserResult

fun RegisterUserResult.InvalidInput.toApiError(): ApiError = SharedErrors.validationFailed(
    RegisterUserRequest::email.name to email.errorMessage(),
    RegisterUserRequest::password.name to password.errorMessage(),
)
