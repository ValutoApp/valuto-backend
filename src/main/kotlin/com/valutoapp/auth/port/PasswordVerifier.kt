package com.valutoapp.auth.port

import com.valutoapp.auth.domain.HashedPassword
import com.valutoapp.auth.domain.PlainPassword

interface PasswordVerifier {
    fun matches(
        plainPassword: PlainPassword,
        hashedPassword: HashedPassword,
    ): Boolean
}
