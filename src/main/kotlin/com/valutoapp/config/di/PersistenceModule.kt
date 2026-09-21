package com.valutoapp.config.di

import com.valutoapp.config.DatabaseSettings
import com.valutoapp.config.DatabaseSettingsReader
import com.valutoapp.persistence.DataSourceFactory
import com.valutoapp.persistence.FlywayMigrationTask
import com.valutoapp.persistence.JooqTransactionManager
import com.valutoapp.shared.port.TransactionManager
import io.ktor.server.plugins.di.DependencyRegistry
import kotlinx.coroutines.Dispatchers
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

fun DependencyRegistry.persistenceModule() {
    provide<DatabaseSettings> {
        DatabaseSettingsReader(resolve()).read()
    }

    provide<DataSource> {
        DataSourceFactory().create(resolve())
    }

    provide<DSLContext> {
        DSL.using(resolve<DataSource>(), SQLDialect.POSTGRES)
    }

    provide<TransactionManager> {
        JooqTransactionManager(
            dsl = resolve(),
            dispatcher = Dispatchers.IO.limitedParallelism(resolve<DatabaseSettings>().maximumPoolSize),
        )
    }
    provide<FlywayMigrationTask> {
        FlywayMigrationTask(
            dataSource = resolve(),
            environmentSettings = resolve(),
        )
    }
}
