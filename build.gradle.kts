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
