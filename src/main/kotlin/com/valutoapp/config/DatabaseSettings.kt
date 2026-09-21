package com.valutoapp.config

private const val MIN_PORT = 1
private const val MAX_PORT = 65535

private const val HOST_KEY = "database.host"
private const val PORT_KEY = "database.port"
private const val NAME_KEY = "database.name"
private const val USERNAME_KEY = "database.username"
private const val PASSWORD_KEY = "database.password"
private const val MAX_POOL_SIZE_KEY = "database.maximumPoolSize"

data class DatabaseSettings(
    val host: String,
    val port: Int,
    val name: String,
    val username: String,
    val password: String,
    val maximumPoolSize: Int,
) {
    val jdbcUrl: String
        get() = "jdbc:postgresql://$host:$port/$name"
}

class DatabaseSettingsReader(
    private val properties: PropertyReader,
) {
    fun read(): DatabaseSettings {
        val port = properties.requiredInt(PORT_KEY)
        validatePort(port)

        val maximumPoolSize = properties.requiredInt(MAX_POOL_SIZE_KEY)
        validateMaxPoolSize(maximumPoolSize)

        return DatabaseSettings(
            host = properties.requiredString(HOST_KEY),
            port = port,
            name = properties.requiredString(NAME_KEY),
            username = properties.requiredString(USERNAME_KEY),
            password = properties.requiredString(PASSWORD_KEY),
            maximumPoolSize = maximumPoolSize,
        )
    }

    private fun validatePort(port: Int) {
        if (port !in MIN_PORT..MAX_PORT) {
            throw InvalidPropertyException(PORT_KEY, port.toString())
        }
    }

    private fun validateMaxPoolSize(maximumPoolSize: Int) {
        if (maximumPoolSize <= 0) {
            throw InvalidPropertyException(MAX_POOL_SIZE_KEY, maximumPoolSize.toString())
        }
    }
}
