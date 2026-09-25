package com.valutoapp.user.api

import com.valutoapp.shared.api.respondError
import com.valutoapp.user.application.RegisterUserCommand
import com.valutoapp.user.application.RegisterUserResult
import com.valutoapp.user.application.RegisterUserUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall

class UserController(private val registerUser: RegisterUserUseCase) {

    suspend fun register(call: RoutingCall) {
        val request = call.receive<RegisterUserRequest>()

        when (val result = registerUser.execute(RegisterUserCommand(request.email, request.password))) {
            is RegisterUserResult.Success -> call.respond(HttpStatusCode.Created, RegisterUserResponse(result.userId.toString()))
            is RegisterUserResult.EmailTaken -> call.respondError(UserErrors.emailTaken(result.email.value))
            is RegisterUserResult.InvalidInput -> call.respondError(result.toApiError())
        }
    }
}
