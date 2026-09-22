package com.valutoapp.user.application

import com.valutoapp.auth.domain.ParsedPlainPassword
import com.valutoapp.user.domain.Email
import com.valutoapp.user.domain.ParsedEmail
import java.util.UUID

sealed interface RegisterUserResult {
    data class Success(val userId: UUID) : RegisterUserResult

    data class EmailTaken(val email: Email) : RegisterUserResult

    data class InvalidInput(val email: ParsedEmail, val password: ParsedPlainPassword) : RegisterUserResult
}
