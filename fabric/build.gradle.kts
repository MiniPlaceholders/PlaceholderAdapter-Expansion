plugins {
    id("fabric-loom")
}

dependencies {
    compileOnly(libs.miniplaceholders)
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.adventure.platform.fabric)
    modImplementation(libs.placeholderapi.fabric)
}
