plugins {
    id("java")
    id("idea")
    id("fabric-loom") version "1.7.3"
}

val MINECRAFT_VERSION: String by rootProject.extra
val PARCHMENT_VERSION: String by rootProject.extra
val PARCHMENT_MC_VERSION: String by rootProject.extra

val FDRF_VERSION: String by rootProject.extra

repositories {
    maven("https://maven.parchmentmc.org/") // parchment mappings

    maven { // FD refabricated
        name = "Greenhouse Maven"
        url = uri("https://repo.greenhouse.house/releases/")
    }
}

loom {
    accessWidenerPath = file("src/main/resources/vegandelight.fabric.accesswidener")
}

dependencies {
    minecraft("com.mojang:minecraft:${MINECRAFT_VERSION}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$PARCHMENT_MC_VERSION:$PARCHMENT_VERSION@zip")
    })
    compileOnly("net.fabricmc:sponge-mixin:0.15.3+mixin.0.8.7")

    modCompileOnly("vectorwing:FarmersDelight:$FDRF_VERSION") {
        isTransitive = false
    }
}

tasks.configureEach {
    group = null
}