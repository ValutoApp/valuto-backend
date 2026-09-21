package com.valutoapp.persistence

import com.valutoapp.app.StartupTask
import com.valutoapp.config.environment.EnvironmentSettings
import org.flywaydb.core.Flyway
import javax.sql.DataSource

class FlywayMigrationTask(
    private val dataSource: DataSource,
    private val environmentSettings: EnvironmentSettings,
) : StartupTask {
    override fun run() {
        if (!environmentSettings.migrateOnStart) return
        Flyway
            .configure()
            .dataSource(dataSource)
            .load()
            .migrate()
    }
}
