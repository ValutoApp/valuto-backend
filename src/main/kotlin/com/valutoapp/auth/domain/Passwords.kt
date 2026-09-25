package com.valutoapp.auth.domain

@JvmInline
value class HashedPassword private constructor(
    val value: String,
) {
    companion object {
        fun of(raw: String): HashedPassword {
            require(raw.isNotBlank())
            return HashedPassword(raw)
        }
    }
}

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

sealed interface ParsedPlainPassword {
    fun errorMessage(): String?

    data class Valid(private val plainPassword: PlainPassword) : ParsedPlainPassword {
        fun get(): PlainPassword = plainPassword
        override fun errorMessage(): String? = null
    }

    data object Blank : ParsedPlainPassword {
        override fun errorMessage(): String = "Password cannot be empty"
    }

    data class TooShort(val minLength: Int, val length: Int) : ParsedPlainPassword {
        override fun errorMessage(): String = "Password cannot be shorter than $minLength characters"
    }

    data class TooLong(val maxLength: Int, val length: Int) : ParsedPlainPassword {
        override fun errorMessage(): String = "Password cannot be longer than $maxLength characters"
    }
}
