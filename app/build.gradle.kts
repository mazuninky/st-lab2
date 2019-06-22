import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))

    implementation(project(":core"))
    implementation(project(":program"))
    implementation(project(":sl"))
    implementation(project(":domain"))
    implementation(project(":data"))
}
