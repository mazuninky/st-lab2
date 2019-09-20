import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))

    implementation(project(":core"))
    implementation(project(":program"))
    implementation(project(":sl"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":state"))

    testImplementation(kotlin("test"))
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}

application {
    mainClassName = "ru.ifmo.st.lab2.app.MainKt"
}


tasks.withType<Test> {
    useJUnitPlatform()
}
