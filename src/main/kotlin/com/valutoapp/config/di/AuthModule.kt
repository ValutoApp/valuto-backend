package com.valutoapp.config.di

import com.valutoapp.auth.infra.Argon2PasswordEncoder
import com.valutoapp.auth.infra.JooqCredentialStore
import com.valutoapp.auth.port.CredentialStore
import com.valutoapp.auth.port.PasswordHasher
import com.valutoapp.auth.port.PasswordVerifier
import com.valutoapp.config.SecuritySettings
import com.valutoapp.config.SecuritySettingsReader
import io.ktor.server.plugins.di.DependencyRegistry

fun DependencyRegistry.authModule() {
    provide<SecuritySettings> {
        SecuritySettingsReader(resolve()).read()
    }
    provide<PasswordHasher> {
        Argon2PasswordEncoder(resolve())
    }
    provide<PasswordVerifier> {
        Argon2PasswordEncoder(resolve())
    }
    provide<CredentialStore> {
        JooqCredentialStore(resolve())
    }
}
