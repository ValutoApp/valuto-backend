package com.valutoapp.persistence

import com.valutoapp.config.DatabaseSettings
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

class DataSourceFactory {
    fun create(settings: DatabaseSettings): DataSource = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = settings.jdbcUrl
            username = settings.username
            password = settings.password
            maximumPoolSize = settings.maximumPoolSize
            driverClassName = "org.postgresql.Driver"
        },
    )
}
