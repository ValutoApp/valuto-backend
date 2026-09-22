package com.valutoapp.config

private const val HASH_PEPPER_KEY = "security.hashPepper"
private const val HASH_SALT_LENGTH_KEY = "security.hashSaltLength"
private const val HASH_MEMORY_KEY = "security.hashMemory"
private const val HASH_ITERATIONS_KEY = "security.hashIterations"
private const val HASH_PARALLELISM_KEY = "security.hashParallelism"
private const val HASH_LENGTH_KEY = "security.hashLength"

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
    fun read(): SecuritySettings {
        val pepper = properties.requiredString(HASH_PEPPER_KEY)
        val saltLength = properties.optionalInt(HASH_SALT_LENGTH_KEY, 16)
        val memoryKb = properties.optionalInt(HASH_MEMORY_KEY, 65_536)
        val iterations = properties.optionalInt(HASH_ITERATIONS_KEY, 5)
        val parallelism = properties.optionalInt(HASH_PARALLELISM_KEY, 2)
        val outputLength = properties.optionalInt(HASH_LENGTH_KEY, 32)

        return SecuritySettings(
            pepper,
            saltLength,
            memoryKb,
            iterations,
            parallelism,
            outputLength,
        )
    }
}
