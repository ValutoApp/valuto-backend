package com.valutoapp.config

import io.ktor.server.config.ApplicationConfig

class PropertyReader(
    internal val applicationConfig: ApplicationConfig,
) {
    fun requiredString(key: String): String =
        runCatching { applicationConfig.property(key).getString() }
            .getOrElse { throw MissingPropertyException(key) }

    fun optionalString(
        key: String,
        default: String,
    ): String =
        try {
            requiredString(key)
        } catch (_: MissingPropertyException) {
            default
        }

    fun requiredInt(key: String): Int {
        val raw = requiredString(key)
        return raw.toIntOrNull()
            ?: throw InvalidPropertyException(
                key,
                raw,
            )
    }

    fun optionalInt(
        key: String,
        default: Int,
    ): Int =
        try {
            requiredInt(key)
        } catch (_: MissingPropertyException) {
            default
        }

    fun requiredBoolean(key: String): Boolean {
        val raw = requiredString(key)
        return runCatching { raw.toBooleanStrict() }
            .getOrElse { throw InvalidPropertyException(key, raw) }
    }

    fun optionalBoolean(
        key: String,
        default: Boolean,
    ): Boolean =
        try {
            requiredBoolean(key)
        } catch (_: MissingPropertyException) {
            default
        }

    inline fun <reified T : Enum<T>> requiredEnum(key: String): T {
        val raw = requiredString(key).trim().uppercase()
        return runCatching { enumValueOf<T>(raw) }
            .getOrElse {
                throw InvalidPropertyException(key, raw)
            }
    }

    inline fun <reified T : Enum<T>> optionalEnum(
        key: String,
        default: T,
    ): T =
        try {
            requiredEnum(key)
        } catch (_: MissingPropertyException) {
            default
        }
}
