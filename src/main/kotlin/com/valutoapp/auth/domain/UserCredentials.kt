package com.valutoapp.auth.domain

import java.util.UUID

data class UserCredentials(
    val userId: UUID,
    val passwordHash: HashedPassword,
)
