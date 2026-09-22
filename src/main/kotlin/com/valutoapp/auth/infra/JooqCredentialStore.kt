package com.valutoapp.auth.infra

import com.valutoapp.auth.domain.UserCredentials
import com.valutoapp.auth.port.CredentialStore
import com.valutoapp.persistence.JooqTx
import com.valutoapp.shared.port.TransactionManager
import com.valutoapp.shared.port.Tx
import org.jooq.generated.tables.references.USER_CREDENTIALS

class JooqCredentialStore(
    private val tx: TransactionManager,
) : CredentialStore {
    override fun save(
        tx: Tx,
        credentials: UserCredentials,
    ) {
        (tx as JooqTx)
            .dsl
            .insertInto(USER_CREDENTIALS)
            .set(USER_CREDENTIALS.USER_ID, credentials.userId)
            .set(USER_CREDENTIALS.PASSWORD_HASH, credentials.passwordHash.value)
            .execute()
    }
}
