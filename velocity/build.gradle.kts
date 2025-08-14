plugins {
    java
    alias(libs.plugins.blossom)
}

dependencies {
    compileOnly(libs.proxybridge)
    compileOnly(libs.miniplaceholders)
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
}

