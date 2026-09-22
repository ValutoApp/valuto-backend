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
