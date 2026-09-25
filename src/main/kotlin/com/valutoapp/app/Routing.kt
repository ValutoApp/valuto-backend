package com.valutoapp.app

import com.valutoapp.user.api.UserController
import com.valutoapp.user.api.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    val userController: UserController by dependencies

    routing {
        userRoutes(userController)
    }
}
