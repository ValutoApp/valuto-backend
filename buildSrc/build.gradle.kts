plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jooq.codegen)
    implementation(libs.flyway.core)
    implementation(libs.flyway.postgres)
    implementation(libs.testcontainers.postgres)
    implementation(libs.postgresql.driver)
}
