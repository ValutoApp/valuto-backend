package com.valutoapp.shared.domain

class CorruptedDataException(
    val entity: String,
    val field: String,
    val entityId: String,
) : RuntimeException()
