package com.valutoapp.app

import com.valutoapp.shared.api.logUnhandled
import com.valutoapp.shared.api.respondError
import com.valutoapp.shared.api.toApiError
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages

private val logger = KotlinLogging.logger { }

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            logger.logUnhandled(cause)
            call.respondError(cause.toApiError())
        }
    }
}
