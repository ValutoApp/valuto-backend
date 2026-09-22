package com.valutoapp.app

import com.valutoapp.user.api.userRoutes
import com.valutoapp.user.application.RegisterUserUseCase
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    val registerUser: RegisterUserUseCase by dependencies

    routing {
        userRoutes(registerUser)
    }
}
