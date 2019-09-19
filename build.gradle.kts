plugins {
    base
    kotlin("jvm") version "1.3.50"
}

repositories {
    jcenter()
}

subprojects {
    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
        maven {
            url = uri("http://maven.icm.edu.pl/artifactory/repo/")
        }
    }
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = "5.4.1"
}
