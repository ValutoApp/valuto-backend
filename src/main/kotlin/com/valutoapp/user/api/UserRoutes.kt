package com.valutoapp.user.api

import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.userRoutes(userController: UserController) {
    route("/users") {
        post {
            userController.register(call)
        }
    }
}
