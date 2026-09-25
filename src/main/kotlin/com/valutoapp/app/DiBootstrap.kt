package com.valutoapp.app

import com.valutoapp.config.di.authModule
import com.valutoapp.config.di.configModule
import com.valutoapp.config.di.environmentModule
import com.valutoapp.config.di.persistenceModule
import com.valutoapp.config.di.startupModule
import com.valutoapp.config.di.userModule
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

fun Application.configureDI() {
    val config = environment.config
    dependencies.configModule(config)
    dependencies.environmentModule()
    dependencies.persistenceModule()
    dependencies.startupModule()
    dependencies.authModule()
    dependencies.userModule()
}
