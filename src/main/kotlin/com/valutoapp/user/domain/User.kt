package com.valutoapp.user.domain

import java.util.UUID

data class User(
    val id: UUID,
    val email: Email,
)
