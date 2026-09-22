package com.valutoapp.auth.port

import com.valutoapp.auth.domain.UserCredentials
import com.valutoapp.shared.port.Tx

interface CredentialStore {
    fun save(tx: Tx, credentials: UserCredentials)
}
