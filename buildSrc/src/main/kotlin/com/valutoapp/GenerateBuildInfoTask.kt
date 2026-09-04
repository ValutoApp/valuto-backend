package com.valutoapp

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

abstract class GenerateBuildInfoTask : DefaultTask() {

    @get:Input
    abstract val version: Property<String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    init {
        outputDirectory.set(project.layout.buildDirectory.dir("resources/main"))
    }

    @TaskAction
    internal fun taskAction() {
        val sha = gitCommand("git", "rev-parse", "HEAD")
        val commitDate = OffsetDateTime
            .parse(gitCommand("git", "log", "-1", "--format=%cI"))
            .withOffsetSameInstant(ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.SECONDS)
            .toString()

        val file = outputDirectory.get().asFile.resolve("build-info.properties")
        file.parentFile.mkdirs()
        file.writeText("""
            version=${version.get()}
            sha256=$sha
            time=$commitDate
        """.trimIndent())
    }

    private fun gitCommand(vararg cmd: String): String {
        val process = ProcessBuilder(*cmd)
            .directory(project.rootDir)
            .redirectErrorStream(true)
            .start()
        val output = process.inputStream.bufferedReader().readText().trim()
        if (process.waitFor() != 0) {
            throw GradleException("Command failed: ${cmd.joinToString(" ")}\n$output")
        }
        return output
    }
}
