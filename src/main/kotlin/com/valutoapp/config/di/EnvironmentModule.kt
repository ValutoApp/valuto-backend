package com.valutoapp.config.di

import com.valutoapp.config.environment.EnvironmentReader
import com.valutoapp.config.environment.EnvironmentSettings
import io.ktor.server.plugins.di.DependencyRegistry

fun DependencyRegistry.environmentModule() {
    provide<EnvironmentSettings> {
        EnvironmentReader(resolve()).read()
    }
}
