plugins {
    java
    alias(libs.plugins.shadow)
}

dependencies {
    compileOnly(libs.adventure.api)
    compileOnly(libs.miniplaceholders)
    implementation(projects.placeholderAdapterExpansionPaper)
    implementation(projects.placeholderAdapterExpansionVelocity)
    implementation(projects.placeholderAdapterExpansionFabric) {
        isTransitive = false
    }
}

allprojects {
    apply<JavaPlugin>()
    tasks {
        withType<JavaCompile> {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(21)
        }
    }

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    shadowJar {
        archiveFileName.set("${rootProject.name}-${project.version}.jar")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    build {
        dependsOn(shadowJar)
    }
}
