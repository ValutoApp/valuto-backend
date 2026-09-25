package com.valutoapp.user.application

import com.valutoapp.auth.domain.ParsedPlainPassword
import com.valutoapp.auth.domain.PlainPassword
import com.valutoapp.auth.domain.UserCredentials
import com.valutoapp.auth.port.CredentialStore
import com.valutoapp.auth.port.PasswordHasher
import com.valutoapp.shared.IdGenerator
import com.valutoapp.shared.port.TransactionManager
import com.valutoapp.user.domain.Email
import com.valutoapp.user.domain.ParsedEmail
import com.valutoapp.user.domain.User
import com.valutoapp.user.port.UserRepository
import java.util.UUID

data class RegisterUserCommand(
    val email: String,
    val plainPassword: String,
)

sealed interface RegisterUserResult {
    data class Success(val userId: UUID) : RegisterUserResult

    data class EmailTaken(val email: Email) : RegisterUserResult

    data class InvalidInput(val email: ParsedEmail, val password: ParsedPlainPassword) : RegisterUserResult
}

class RegisterUserUseCase(
    private val userRepository: UserRepository,
    private val credentialStore: CredentialStore,
    private val passwordHasher: PasswordHasher,
    private val idGenerator: IdGenerator,
    private val txManager: TransactionManager,
) {
    suspend fun execute(command: RegisterUserCommand): RegisterUserResult {
        val parsedEmail = Email.parse(command.email)
        val parsedPassword = PlainPassword.parse(command.plainPassword)

        if (parsedEmail !is ParsedEmail.Valid || parsedPassword !is ParsedPlainPassword.Valid) {
            return RegisterUserResult.InvalidInput(parsedEmail, parsedPassword)
        }

        return register(parsedEmail.get(), parsedPassword.get())
    }

    private suspend fun register(email: Email, plainPassword: PlainPassword): RegisterUserResult {
        if (userRepository.findByEmail(email) != null) {
            return RegisterUserResult.EmailTaken(email)
        }

        val user = User(idGenerator.next(), email)
        val credentials = UserCredentials(user.id, passwordHasher.hash(plainPassword))

        txManager.transaction {
            userRepository.save(this, user)
            credentialStore.save(this, credentials)
        }

        return RegisterUserResult.Success(user.id)
    }
}
