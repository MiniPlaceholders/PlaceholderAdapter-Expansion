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
    }
}


plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

arrayOf("paper", "velocity").forEach {
    include("placeholderapi-expansion-$it")

    project(":placeholderapi-expansion-$it").projectDir = file(it)
}

