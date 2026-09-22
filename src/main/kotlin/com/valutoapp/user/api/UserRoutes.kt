package com.valutoapp.user.api

import com.valutoapp.user.application.RegisterUserCommand
import com.valutoapp.user.application.RegisterUserUseCase
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.userRoutes(registerUser: RegisterUserUseCase) {
    post("/users") {
        val request = call.receive<RegisterUserRequest>()
        val result = registerUser.execute(RegisterUserCommand(request.email, request.password))
        call.respond(result)
    }
}
