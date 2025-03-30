@file:Suppress("UnstableApiUsage")

plugins {
    id("fabric-loom") version ("1.10-SNAPSHOT")
}

repositories {
    maven { // FD refabricated
        name = "Greenhouse Maven"
        url = uri("https://repo.greenhouse.house/releases/")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${rootProject.properties["minecraft_version"]}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${rootProject.properties["parchment_version"]}@zip")
    })

    // mixin extras is included by default in both fabric and neoforge (no additional dependency required)
    compileOnly(annotationProcessor("io.github.llamalad7:mixinextras-common:0.3.5")!!)

    compileOnly("net.fabricmc:sponge-mixin:0.15.3+mixin.0.8.7")
    modImplementation("net.fabricmc:fabric-loader:${rootProject.properties["fabric_loader_version"]}")


    modCompileOnly("vectorwing:FarmersDelight:${rootProject.properties["fdrf_version"]}") {
        isTransitive = false
    }
}

loom {
    accessWidenerPath = file("src/main/resources/vegandelight.fabric.accesswidener")

    mixin {
        useLegacyMixinAp = false
    }
}

// don't generate jar files for the common code
tasks {
    jar { enabled = false }
    remapJar { enabled = false }
}
