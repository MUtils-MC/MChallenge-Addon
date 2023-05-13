
plugins {
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.serialization") version "1.8.20"
    id("io.papermc.paperweight.userdev") version "1.5.0"
    id("xyz.jpenilla.run-paper") version "1.1.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.miraculixx"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // The MChallenge API
    compileOnly("de.miraculixx:challenge-api:1.2.1")

    // The KSpigot version is not related to the game version
    implementation("net.axay:kspigot:1.19.2")

    // Define your minecraft version
    paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")

    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.13.1")
}



java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    shadowJar {
        dependencies {
            include(dependency("net.axay:kspigot:1.19.2"))
        }
    }
    assemble {
        dependsOn(shadowJar)
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}