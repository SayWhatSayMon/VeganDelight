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
    maven("https://maven.parchmentmc.org/")

    maven {
        name = "Greenhouse Maven"
        url = uri("https://repo.greenhouse.house/releases/")
    }
}

base {
    archivesName.set("vegan-delight-fabric-${MINECRAFT_VERSION}")
}

dependencies {
    minecraft("com.mojang:minecraft:${MINECRAFT_VERSION}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$PARCHMENT_MC_VERSION:$PARCHMENT_VERSION@zip")
    })

    modCompileOnly("vectorwing:FarmersDelight:$FDRF_VERSION") {
        isTransitive = false
    }
}

tasks.configureEach {
    group = null
}