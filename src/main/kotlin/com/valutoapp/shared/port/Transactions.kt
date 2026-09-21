package com.valutoapp.shared.port

interface Tx

interface TransactionManager {
    suspend fun <T> query(block: Tx.() -> T): T

    suspend fun <T> transaction(block: Tx.() -> T): T
}
