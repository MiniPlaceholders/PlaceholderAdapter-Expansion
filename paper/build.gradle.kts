plugins {
    java
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.miniplaceholders)
    compileOnly(libs.placeholderapi)
}

tasks {
    processResources {
        filesMatching("paper-plugin.yml") {
            expand("version" to project.version)
        }
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
