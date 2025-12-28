plugins {
    id("java")
    id("java-library")
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.cachewrapper"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    api("org.springframework.boot:spring-boot-flyway:4.0.1")
    api("org.springframework.boot:spring-boot-starter-web:4.0.1")
    api("org.springframework.boot:spring-boot-starter:4.0.1")
    api("org.springframework.boot:spring-boot-starter-data-jpa:4.0.1")
    api("org.springframework.kafka:spring-kafka:4.0.1")
    api("org.flywaydb:flyway-database-postgresql:11.20.0")
    api("org.postgresql:postgresql:42.7.8")
    api("org.jetbrains:annotations:26.0.2-1")
    api("com.google.code.gson:gson:2.13.2")

    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
}