package com.valutoapp.auth.infra

import com.password4j.Argon2Function
import com.password4j.Password
import com.password4j.types.Argon2
import com.valutoapp.auth.domain.HashedPassword
import com.valutoapp.auth.domain.PlainPassword
import com.valutoapp.auth.port.PasswordHasher
import com.valutoapp.auth.port.PasswordVerifier
import com.valutoapp.config.SecuritySettings

class Argon2PasswordEncoder(private val settings: SecuritySettings) :
    PasswordHasher,
    PasswordVerifier {

    private val hashingFunction = Argon2Function.getInstance(
        settings.memoryKb,
        settings.iterations,
        settings.parallelism,
        settings.outputLength,
        Argon2.ID,
    )

    override fun hash(plainPassword: PlainPassword): HashedPassword {
        val hash = Password
            .hash(plainPassword.value)
            .addRandomSalt(settings.saltLength)
            .addPepper(settings.pepper)
            .with(hashingFunction)
            .result

        return HashedPassword.of(hash)
    }

    override fun matches(plainPassword: PlainPassword, hashedPassword: HashedPassword): Boolean = Password
        .check(plainPassword.value, hashedPassword.value)
        .addPepper(settings.pepper)
        .with(hashingFunction)
}
