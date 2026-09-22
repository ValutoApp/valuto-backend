package com.valutoapp.user.api

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserRequest(
    val email: String,
    val password: String,
)

@Serializable
data class RegisterUserResponse(
    val userId: String,
)
