package com.valutoapp.shared.domain

interface Validatable {
    val valid: Boolean

    fun errorMessage(): String?
}
