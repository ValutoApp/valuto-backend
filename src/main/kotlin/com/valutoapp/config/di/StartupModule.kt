package com.valutoapp.config.di

import com.valutoapp.app.StartupTask
import com.valutoapp.persistence.FlywayMigrationTask
import io.ktor.server.plugins.di.DependencyRegistry

fun DependencyRegistry.startupModule() {
    provide<List<StartupTask>> {
        listOf(
            resolve<FlywayMigrationTask>(),
        )
    }
}
