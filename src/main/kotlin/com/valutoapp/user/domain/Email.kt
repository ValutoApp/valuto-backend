package com.valutoapp.user.domain

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
                normalized.length > MAX_LENGTH -> ParsedEmail.TooLong(MAX_LENGTH, normalized.length)
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

sealed interface ParsedEmail {
    fun errorMessage(): String?

    data class Valid(private val email: Email) : ParsedEmail {
        fun get(): Email = email
        override fun errorMessage(): String? = null
    }
    data object Blank : ParsedEmail {
        override fun errorMessage(): String = "Email cannot be blank"
    }
    data class TooLong(val maxLength: Int, val length: Int) : ParsedEmail {
        override fun errorMessage(): String = "Email cannot be longer than $maxLength characters, got $length"
    }
    data class Malformed(val raw: String) : ParsedEmail {
        override fun errorMessage(): String = "Malformed email: $raw"
    }
}
