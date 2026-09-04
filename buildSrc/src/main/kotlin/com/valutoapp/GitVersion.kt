package com.valutoapp

import org.gradle.api.GradleException
import java.io.File

fun getVersionFromGitTag(rootDir: File): String {
    fun command(vararg cmd: String): String {
        val process = ProcessBuilder(*cmd)
            .directory(rootDir)
            .redirectErrorStream(true)
            .start()
        val output = process.inputStream.bufferedReader().readText().trim()
        if (process.waitFor() != 0) {
            throw GradleException("Command failed: ${cmd.joinToString(" ")}\n$output")
        }
        return output
    }

    val tag = command("git", "describe", "--tags", "--abbrev=0")
        .ifEmpty { throw GradleException("No git tags found. Cannot determine version.") }
    val base = tag.removePrefix("v")
    val count = command("git", "rev-list", "--count", "$tag..HEAD").toInt()

    return if (count == 0) base else "$base+$count"
}
