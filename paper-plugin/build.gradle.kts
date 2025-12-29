plugins {
    id("java")

    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
    id("com.gradleup.shadow") version "9.3.0"
}

group = "org.cachewrapper"
version = "1.0"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")

    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21.10-R0.1-SNAPSHOT")
    implementation("org.incendo:cloud-paper:2.0.0-beta.10")
    implementation("org.jetbrains:annotations:26.0.2-1")
    implementation("com.google.inject:guice:7.0.0")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.6")

    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
}

tasks.assemble {
    dependsOn(tasks.reobfJar)
}

tasks.shadowJar {
    archiveBaseName.set("netbank-minecraft-plugin")
    archiveVersion.set("")
    archiveClassifier.set("")
}