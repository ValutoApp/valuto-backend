package com.valutoapp.user.infra

import com.valutoapp.persistence.JooqTx
import com.valutoapp.shared.domain.CorruptedDataException
import com.valutoapp.shared.port.TransactionManager
import com.valutoapp.shared.port.Tx
import com.valutoapp.user.domain.Email
import com.valutoapp.user.domain.ParsedEmail
import com.valutoapp.user.domain.User
import com.valutoapp.user.port.UserRepository
import org.jooq.generated.tables.references.USERS

class UserRepositoryJooq(private val tx: TransactionManager) : UserRepository {

    override suspend fun findByEmail(email: Email): User? = tx.query {
        (this as JooqTx).dsl
            .selectFrom(USERS)
            .where(USERS.EMAIL.eq(email.value))
            .fetchOne()?.let { r -> User(r.id, parseEmailOrThrow(r.email, r.id.toString())) }
    }

    override fun save(tx: Tx, user: User) {
        val dsl = (tx as JooqTx).dsl
        dsl
            .insertInto(USERS)
            .set(USERS.ID, user.id)
            .set(USERS.EMAIL, user.email.value)
            .execute()
    }

    private fun parseEmailOrThrow(raw: String, userId: String): Email = when (val parsedEmail = Email.parse(raw)) {
        is ParsedEmail.Valid -> return parsedEmail.get()
        else -> throw CorruptedDataException(USERS.name, USERS.EMAIL.name, userId)
    }
}
