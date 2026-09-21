package com.valutoapp.persistence

import com.valutoapp.shared.port.TransactionManager
import com.valutoapp.shared.port.Tx
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jooq.DSLContext

internal class JooqTx(
    val dsl: DSLContext,
) : Tx

class JooqTransactionManager(
    private val dsl: DSLContext,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : TransactionManager {
    override suspend fun <T> query(block: Tx.() -> T): T =
        withContext(dispatcher) {
            JooqTx(dsl).block()
        }

    override suspend fun <T> transaction(block: Tx.() -> T): T =
        withContext(dispatcher) {
            dsl.transactionResult { config ->
                JooqTx(config.dsl()).block()
            }
        }
}
