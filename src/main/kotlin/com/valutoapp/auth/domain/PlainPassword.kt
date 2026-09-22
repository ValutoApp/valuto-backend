package com.valutoapp.auth.domain

import com.valutoapp.shared.domain.Validatable

@JvmInline
value class PlainPassword private constructor(val value: String) {
    companion object {
        private const val MIN_LENGTH = 8
        private const val MAX_LENGTH = 256

        fun parse(raw: String): ParsedPlainPassword = when {
            raw.isBlank() -> ParsedPlainPassword.Blank
            raw.length < MIN_LENGTH -> ParsedPlainPassword.TooShort(MIN_LENGTH, raw.length)
            raw.length > MAX_LENGTH -> ParsedPlainPassword.TooLong(MAX_LENGTH, raw.length)
            else -> ParsedPlainPassword.Valid(PlainPassword(raw))
        }
    }
}

sealed interface ParsedPlainPassword : Validatable {
    data class Valid(private val plainPassword: PlainPassword) : ParsedPlainPassword {
        fun get(): PlainPassword = plainPassword
        override val valid = true
        override fun errorMessage(): String? = null
    }

    data object Blank : ParsedPlainPassword {
        override val valid = false
        override fun errorMessage(): String = "Password cannot be empty"
    }

    data class TooShort(val minLength: Int, val length: Int) : ParsedPlainPassword {
        override val valid = false
        override fun errorMessage(): String = "Password cannot be shorter than ${this.minLength} characters"
    }

    data class TooLong(val maxLength: Int, val length: Int) : ParsedPlainPassword {
        override val valid = false
        override fun errorMessage(): String = "Password cannot be longer than ${this.maxLength} characters"
    }
}
