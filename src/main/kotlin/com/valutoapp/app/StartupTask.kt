package com.valutoapp.app

import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies

interface StartupTask {
    fun run()
}

fun Application.runStartupTasks() {
    val tasks: List<StartupTask> by dependencies
    tasks.forEach { it.run() }
}
