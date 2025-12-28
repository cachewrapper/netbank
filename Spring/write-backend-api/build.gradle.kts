plugins {
    id("java")
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
}

repositories {
    mavenCentral()
}

group = "org.cachewrapper"
version = "1.0"

dependencies {
    implementation(project(":Spring:common-backend"))

    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
}