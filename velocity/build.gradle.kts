plugins {
    java
    alias(libs.plugins.blossom)
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(libs.proxybridge)
    compileOnly(libs.miniplaceholders)
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

blossom {
    replaceTokenIn("src/main/java/io/github/miniplaceholders/expansion/placeholderapi/velocity/VelocityPlugin.java")
    replaceToken("{version}", project.version)
}
