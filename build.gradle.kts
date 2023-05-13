
plugins {
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.serialization") version "1.8.10"
    id("io.papermc.paperweight.userdev") version "1.5.0"
    id("xyz.jpenilla.run-paper") version "1.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    // The MChallenge API
    implementation("de.miraculixx:challenge-api:1.2.0")

    // The KSpigot version is not related to the game version
    implementation("net.axay:kspigot:1.19.2")

    // Define your minecraft version
    paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}