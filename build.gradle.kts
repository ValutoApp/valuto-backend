plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

group = "com.valutoapp"
version = com.valutoapp.getVersionFromGitTag(rootDir)

application {
    mainClass = "io.ktor.server.netty.EngineMain"
    applicationDefaultJvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25)
        allWarningsAsErrors.set(true)
    }
    jvmToolchain(25)
}

ktlint {
    version.set(libs.versions.ktlint.core)
}

dependencies {
    platform(libs.ktor.bom)

    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.di)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.serialization.json)
    implementation(libs.logback.classic)
    implementation(libs.jooq.core)
    implementation(libs.hikari.cp)

    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.assertions.ktor)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.register<Exec>("composeUp") {
    description = "Setup necessary docker containers for development"
    environment("POSTGRES_IMAGE_VERSION", providers.gradleProperty("postgresImageVersion").get())
    commandLine("docker", "compose", "up", "-d")
}

tasks.register<com.valutoapp.GenerateJooqTask>("generateJooq") {
    description = "Generates JOOQ classes for database schema"
}

tasks.register<com.valutoapp.GenerateBuildInfoTask>("generateBuildInfo") {
    description = "Generates build-info.properties with version, commit sha and date"
}

tasks.named("compileKotlin") {
    dependsOn("generateJooq")
}

tasks.named("processResources") {
    dependsOn("generateBuildInfo")
}
