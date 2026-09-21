package com.valutoapp.config.di

import com.valutoapp.config.PropertyReader
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.plugins.di.DependencyRegistry

fun DependencyRegistry.configModule(config: ApplicationConfig) {
    provide<PropertyReader> {
        PropertyReader(config)
    }
}
