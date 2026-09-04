package com.valutoapp

import org.flywaydb.core.Flyway
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Target
import org.testcontainers.postgresql.PostgreSQLContainer

abstract class GenerateJooqTask : DefaultTask() {

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val migrations: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    internal fun taskAction() {
        initializeContainer().use { container ->
            flywayMigrate(container)
            generateJooq(container)
        }
    }

    private fun initializeContainer(): PostgreSQLContainer {
        val containerVersion: String = project.providers.gradleProperty("postgresImageVersion").get()
        val container = PostgreSQLContainer("postgres:${containerVersion}")
        container.start()
        return container
    }

    private fun flywayMigrate(container: PostgreSQLContainer) {
        Flyway
            .configure()
            .dataSource(container.jdbcUrl, container.username, container.password)
            .locations("filesystem:${migrations.get().asFile.absolutePath}")
            .load()
            .migrate()
    }

    private fun generateJooq(container: PostgreSQLContainer) {
        GenerationTool.generate(
            Configuration()
                .withJdbc(
                    Jdbc()
                        .withDriver("org.postgresql.Driver")
                        .withUrl(container.jdbcUrl)
                        .withUser(container.username)
                        .withPassword(container.password)
                )
                .withGenerator(
                    Generator()
                        .withName("org.jooq.codegen.KotlinGenerator")
                        .withDatabase(
                            Database()
                                .withInputSchema("public")
                                .withExcludes("flyway_schema_history")
                        )
                        .withGenerate(
                            Generate()
                                .withDeprecated(false)
                                .withKotlinNotNullRecordAttributes(true)
                        )
                        .withTarget(
                            Target()
                                .withDirectory(outputDirectory.get().asFile.absolutePath)
                        )
                )
        )
    }
}