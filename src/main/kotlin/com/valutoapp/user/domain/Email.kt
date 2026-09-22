package com.valutoapp.user.domain

import com.valutoapp.shared.domain.Validatable

@JvmInline
value class Email private constructor(
    val value: String,
) {
    companion object {
        private const val MAX_LENGTH = 256

        fun parse(raw: String): ParsedEmail {
            val normalized = raw.trim().lowercase()
            return when {
                raw.isBlank() -> ParsedEmail.Blank
                normalized.length > MAX_LENGTH -> ParsedEmail.TooLong(normalized.length, MAX_LENGTH)
                normalized.count { it == '@' } != 1 -> ParsedEmail.Malformed(raw)
                normalized.any { it.isWhitespace() } -> ParsedEmail.Malformed(raw)
                !isValidStructure(normalized) -> ParsedEmail.Malformed(raw)
                else -> ParsedEmail.Valid(Email(normalized))
            }
        }

        private fun isValidStructure(normalized: String): Boolean {
            val (local, domain) = normalized.split('@')
            return local.isNotEmpty() && domain.isNotEmpty() && '.' in domain && domain.split('.').none { it.isEmpty() }
        }
    }
}

sealed interface ParsedEmail : Validatable {
    data class Valid(private val email: Email) : ParsedEmail {
        fun get(): Email = email
        override val valid = true
        override fun errorMessage(): String? = null
    }
    data object Blank : ParsedEmail {
        override val valid = false
        override fun errorMessage(): String = "Email cannot be blank"
    }
    data class TooLong(val length: Int, val max: Int) : ParsedEmail {
        override val valid = false
        override fun errorMessage(): String = "Email cannot be longer than ${this.max} characters, got ${this.length}"
    }
    data class Malformed(val raw: String) : ParsedEmail {
        override val valid = false
        override fun errorMessage(): String = "Malformed email: ${this.raw}"
    }
}
