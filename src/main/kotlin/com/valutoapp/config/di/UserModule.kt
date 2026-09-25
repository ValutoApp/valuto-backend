package com.valutoapp.config.di

import com.valutoapp.shared.IdGenerator
import com.valutoapp.shared.RandomUuidGenerator
import com.valutoapp.user.api.UserController
import com.valutoapp.user.application.RegisterUserUseCase
import com.valutoapp.user.infra.UserRepositoryJooq
import com.valutoapp.user.port.UserRepository
import io.ktor.server.plugins.di.DependencyRegistry

fun DependencyRegistry.userModule() {
    provide<IdGenerator> {
        RandomUuidGenerator()
    }
    provide<UserRepository> {
        UserRepositoryJooq(resolve())
    }
    provide<RegisterUserUseCase> {
        RegisterUserUseCase(
            userRepository = resolve(),
            credentialStore = resolve(),
            passwordHasher = resolve(),
            idGenerator = resolve(),
            txManager = resolve(),
        )
    }
    provide<UserController> {
        UserController(resolve())
    }
}
