package com.valutoapp.shared.api

import kotlinx.serialization.Serializable

@Serializable
data class FieldViolation(
    val field: String,
    val message: String,
)
