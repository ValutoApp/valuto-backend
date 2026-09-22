package com.valutoapp.user.application

data class RegisterUserCommand(
    val email: String,
    val plainPassword: String,
)
