@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "PlaceholderAdapter-Expansion"

dependencyResolutionManagement {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://central.sonatype.com/repository/maven-snapshots/")
        maven("https://jitpack.io")
        maven("https://repo.william278.net/releases/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven("https://maven.nucleoid.xyz/") { name = "Nucleoid" }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net/")
    }
}


plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("fabric-loom") version "1.11.7"
}

arrayOf("paper", "velocity", "fabric").forEach {
    include("placeholder-adapter-expansion-$it")

    project(":placeholder-adapter-expansion-$it").projectDir = file(it)
}

