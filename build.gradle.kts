import org.sonarqube.gradle.SonarTask

group = "com.riza"
version = "0.0.1-SNAPSHOT"
description = "APIPromo"
java.sourceCompatibility = JavaVersion.VERSION_17

val mockitoVersion = "5.2.1"
val jacksonVersion = "2.10.3"
val mysqlVersion = "8.0.33"

plugins {
    val kotlinVersion = "1.9.20"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("io.spring.dependency-management") version "1.1.4"
    id("org.springframework.boot") version "3.2.3"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("org.sonarqube") version "4.4.1.3373"
    id("jacoco")
}

repositories {
    mavenCentral()
}
configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {

    additionalEditorconfig.set(
        mapOf(
            "ktlint_standard_no-wildcard-imports" to "disabled",
        ),
    )
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("mysql:mysql-connector-java:$mysqlVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoVersion")
    testImplementation("org.springframework.security:spring-security-test")
}

sonar {
    properties {
        property("sonar.projectKey", "temp")
        property("sonar.organization", "temp")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

tasks.withType<SonarTask> {
    dependsOn(tasks.named("test"))
    dependsOn(tasks.named("jacocoTestReport"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}
