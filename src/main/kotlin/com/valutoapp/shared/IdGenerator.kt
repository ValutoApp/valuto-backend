package com.valutoapp.shared

import java.util.UUID

interface IdGenerator {
    fun next(): UUID
}

class RandomUuidGenerator : IdGenerator {
    override fun next(): UUID = UUID.randomUUID()
}
