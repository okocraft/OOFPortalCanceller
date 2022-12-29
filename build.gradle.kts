plugins {
    java
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

group = "net.okocraft"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")

    implementation("org.jetbrains:annotations:23.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.compileJava {
    options.release.set(17)
}

tasks.test {
    useJUnitPlatform()
}
