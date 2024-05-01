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
    id("org.openapi.generator") version "7.5.0"
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("io.spring.dependency-management") version "1.1.4"
    id("org.springframework.boot") version "3.2.4"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("org.sonarqube") version "5.0.0.4638"
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

openApiGenerate {
    generatorName.set("kotlin-spring")

    inputSpec.set("$rootDir/APIPromo.yaml")
    outputDir.set("$buildDir/generated")
    packageName.set("com.riza.apipromo")

    configOptions.apply {
        put("title", "APIPromo API v1")
        put("server-port", "8080")
        put("library", "spring-boot")
        put("documentationProvider", "none")
        put("serializationLibrary", "jackson")
        put("sourceFolder", "src/main/kotlin")
        put("enumPropertyNaming", "UPPERCASE")
        put("useSpringBoot3", "true")
        put("apiPackage", "com.riza.apipromo.v1")
        put("modelPackage", "com.riza.apipromo.v1.domain")
        put("basePackage", "com.riza.apipromo.v1")
        put("interfaceOnly", "true")
        put("serviceInterface", "false")
        put("serviceImplementation", "false")
        put("reactive", "false")
        put("useBeanValidation", "false")
    }
}

tasks {
    named("openApiGenerate") {
        dependsOn(named("ktlintCheck"))
    }

    compileJava {
        dependsOn(openApiGenerate)
    }

    compileKotlin {
        dependsOn(openApiGenerate)
    }
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDirs("$buildDir/generated/src/main/kotlin")
        }
    }
}

ktlint {
    filter {
        exclude { element -> element.file.path.contains("generated/") }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("mysql:mysql-connector-java:$mysqlVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoVersion")
    testImplementation("org.springframework.security:spring-security-test")
}

sonar {
    properties {
        property("sonar.projectKey", "jcro15testingorg_csdt-project")
        property("sonar.organization", "jcro15testingorg")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}
tasks.named("check") {
    dependsOn(tasks.named("ktlintCheck"))
}

tasks.withType<SonarTask> {
    dependsOn(tasks.named("check"))
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
