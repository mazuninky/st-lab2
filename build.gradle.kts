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
    }
}
