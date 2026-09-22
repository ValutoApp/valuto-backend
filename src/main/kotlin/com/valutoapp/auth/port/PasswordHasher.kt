package com.valutoapp.auth.port

import com.valutoapp.auth.domain.HashedPassword
import com.valutoapp.auth.domain.PlainPassword

interface PasswordHasher {
    fun hash(plainPassword: PlainPassword): HashedPassword
}
