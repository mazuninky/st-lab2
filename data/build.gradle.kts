import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.flywaydb.flyway").version("6.0.2")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.3.50"
}

val spek_version = "2.0.2"

flyway {
    url = "jdbc:postgresql://localhost:5432/todo"
    user = "todo"
    password = "12345"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val ktor_version: String by project

dependencies {
    compile("org.postgresql:postgresql:42.2.5")

    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":core"))

    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-auth-jvm:$ktor_version")

    implementation("com.oracle.jdbc:ojdbc8:19.3.0.0")
    implementation("com.h2database:h2:1.4.199")
    implementation("org.postgresql:postgresql:42.2.5")
    implementation("com.zaxxer:HikariCP:3.3.1")

    testImplementation("org.amshove.kluent:kluent:1.49")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek_version")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek_version") {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.junit.platform")
    }
    testRuntimeOnly(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.12.0")
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek")
    }
}
