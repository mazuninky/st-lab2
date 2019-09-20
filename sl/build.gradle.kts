import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

val spek_version = "2.0.2"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

//    testImplementation("org.amshove.kluent:kluent:1.49")
//    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek_version")
//    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek_version") {
//        exclude(group = "org.jetbrains.kotlin")
//        exclude(group = "org.junit.platform")
//    }
}

//tasks.withType<Test> {
//    useJUnitPlatform {
//        includeEngines("spek")
//    }
//}
