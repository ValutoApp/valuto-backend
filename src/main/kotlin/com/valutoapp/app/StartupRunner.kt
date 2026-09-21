package com.valutoapp.app

import com.valutoapp.persistence.FlywayMigrationTask
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

fun Application.runStartupTasks() {
    val migrationTask: FlywayMigrationTask by dependencies
    migrationTask.run()
}
