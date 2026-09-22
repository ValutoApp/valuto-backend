package com.valutoapp.user.port

import com.valutoapp.shared.port.Tx
import com.valutoapp.user.domain.Email
import com.valutoapp.user.domain.User

interface UserRepository {
    suspend fun findByEmail(email: Email): User?

    fun save(tx: Tx, user: User)
}
