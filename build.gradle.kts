plugins {
    java
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(projects.placeholderapiExpansionPaper)
    implementation(projects.placeholderapiExpansionVelocity)
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
