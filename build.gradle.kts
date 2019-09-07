plugins {
    base
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
