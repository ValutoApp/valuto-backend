package com.valutoapp

import com.valutoapp.app.configureDI
import com.valutoapp.app.configureLogging
import com.valutoapp.app.configureRouting
import com.valutoapp.app.configureSerialization
import com.valutoapp.app.configureStatusPages
import com.valutoapp.app.runStartupTasks
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureDI()
    configureLogging()
    configureSerialization()
    configureStatusPages()
    runStartupTasks()
    configureRouting()
}
