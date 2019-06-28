plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    id("org.flywaydb.flyway").version("5.2.4")
}

val spek_version = "2.0.2"

flyway {
    url = "jdbc:postgresql://localhost:5432/todo"
    user = "todo"
    password = "12345"
}

dependencies {
    compile("org.postgresql:postgresql:42.2.5")

    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":core"))

    implementation("com.oracle.jdbc:ojdbc8:18.3.0.0")
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
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek")
    }
}
