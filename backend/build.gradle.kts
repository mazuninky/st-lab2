import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project

plugins {
    application
    kotlin("jvm")
    id("com.google.cloud.tools.jib") version "1.3.0"
}

group = "ru.ifmo.lab2"
version = "1.0.0"

val mainClass = "io.ktor.server.jetty.EngineMain"

application {
    mainClassName = mainClass
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-server-jetty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    testCompile("io.ktor:ktor-server-tests:$ktor_version")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

jib {
//    to {
//        image = "st-lab2/backend"
//    }
    container {

        ports = listOf("8080")
        mainClass = mainClass


        jvmFlags = listOf(
                "-server",
                "-Djava.awt.headless=true",
                "-XX:InitialRAMFraction=2",
                "-XX:MinRAMFraction=2",
                "-XX:MaxRAMFraction=2",
                "-XX:+UseG1GC",
                "-XX:MaxGCPauseMillis=100",
                "-XX:+UseStringDeduplication"
        )
    }

}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
