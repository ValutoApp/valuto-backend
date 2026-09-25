package com.valutoapp.config

private const val HASH_PEPPER_KEY = "security.hashPepper"
private const val HASH_SALT_LENGTH_KEY = "security.hashSaltLength"
private const val HASH_MEMORY_KEY = "security.hashMemory"
private const val HASH_ITERATIONS_KEY = "security.hashIterations"
private const val HASH_PARALLELISM_KEY = "security.hashParallelism"
private const val HASH_LENGTH_KEY = "security.hashLength"

private const val DEFAULT_HASH_SALT_LENGTH = 16
private const val DEFAULT_HASH_MEMORY_MB = 65_536
private const val DEFAULT_HASH_ITERATIONS = 5
private const val DEFAULT_HASH_PARALLELISM = 2
private const val DEFAULT_HASH_LENGTH = 32

data class SecuritySettings(
    val pepper: String,
    val saltLength: Int,
    val memoryKb: Int,
    val iterations: Int,
    val parallelism: Int,
    val outputLength: Int,
)

class SecuritySettingsReader(
    private val properties: PropertyReader,
) {
    fun read() = SecuritySettings(
        pepper = properties.requiredString(HASH_PEPPER_KEY),
        saltLength = properties.optionalInt(HASH_SALT_LENGTH_KEY, DEFAULT_HASH_SALT_LENGTH),
        memoryKb = properties.optionalInt(HASH_MEMORY_KEY, DEFAULT_HASH_MEMORY_MB),
        iterations = properties.optionalInt(HASH_ITERATIONS_KEY, DEFAULT_HASH_ITERATIONS),
        parallelism = properties.optionalInt(HASH_PARALLELISM_KEY, DEFAULT_HASH_PARALLELISM),
        outputLength = properties.optionalInt(HASH_LENGTH_KEY, DEFAULT_HASH_LENGTH),
    )
}
