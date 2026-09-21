package com.valutoapp.config.environment

import com.valutoapp.config.PropertyReader

private const val ENVIRONMENT_KEY = "app.environment"
private const val DEPLOYMENT_KEY = "app.deployment"
private const val MIGRATE_ON_START_KEY = "app.migrateOnStart"

data class EnvironmentSettings(
    val environment: Environment,
    val deployment: Deployment,
    val migrateOnStart: Boolean,
)

class EnvironmentReader(
    private val properties: PropertyReader,
) {
    fun read(): EnvironmentSettings {
        val environment = properties.requiredEnum<Environment>(ENVIRONMENT_KEY)
        val deployment = properties.requiredEnum<Deployment>(DEPLOYMENT_KEY)
        val migrateOnStart = properties.optionalBoolean(MIGRATE_ON_START_KEY, environment == Environment.DEV)
        return EnvironmentSettings(environment, deployment, migrateOnStart)
    }
}
