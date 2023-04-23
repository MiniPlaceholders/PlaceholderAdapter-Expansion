plugins {
    java
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":placeholderapi-expansion-paper"))
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
