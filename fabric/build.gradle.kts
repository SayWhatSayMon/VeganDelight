val MINECRAFT_VERSION: String by rootProject.extra
val PARCHMENT_VERSION: String by rootProject.extra
val PARCHMENT_MC_VERSION: String by rootProject.extra

val FABRIC_LOADER_VERSION: String by rootProject.extra
val FABRIC_API_VERSION: String by rootProject.extra

val FDRF_VERSION: String by rootProject.extra


plugins {
    id("java")
    id("idea")
    id("fabric-loom") version "1.7.3"
}

repositories {
    maven("https://maven.parchmentmc.org/") // Parchment mappings

    maven("https://repo.greenhouse.house/releases/") { // Farmer's Delight Refabricated
        name = "Greenhouse Maven"
    }
    // maven("https://mvn.devos.one/releases/") // Porting Lib // not officially released for 1.21 yet
    maven("https://repo.greenhouse.house/snapshots/") { // Temporary Porting Lib Fork for 1.21
        name = "Greenhouse Maven (Snapshots)"
    }
    maven("https://maven.jamieswhiteshirt.com/libs-release") { // Reach Entity Attributes (Required by Porting Lib)
        content {
            includeGroup("com.jamieswhiteshirt")
        }
    }
    maven("https://jitpack.io/") { // Fabric ASM
        content {
            excludeGroup("io.github.fabricators_of_create.Porting-Lib")
        }
    }
}

loom {
    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
        }
        named("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run")
        }
    }
}

tasks {
    withType<JavaCompile> {
        source(project(":common").sourceSets.main.get().allSource)
    }

    remapJar.get().destinationDirectory = rootDir.resolve("build").resolve("libs")

    javadoc { source(project(":common").sourceSets.main.get().allJava) }

    processResources {
        from(project(":common").sourceSets.main.get().resources)

        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(
                mapOf(
                    "version" to project.version,
                    "loader_version" to FABRIC_LOADER_VERSION,
                    "minecraft_version" to MINECRAFT_VERSION,
                )
            )
        }
    }

    named("compileTestJava").configure {
        enabled = false
    }

    named("test").configure {
        enabled = false
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${MINECRAFT_VERSION}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$PARCHMENT_MC_VERSION:$PARCHMENT_VERSION@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:${FABRIC_LOADER_VERSION}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${FABRIC_API_VERSION}")

    modImplementation("vectorwing:FarmersDelight:${FDRF_VERSION}") {
        exclude("net.fabricmc")
        // exclude("io.github.fabricators_of_create.Porting-Lib")
    }

    implementation(project.project(":common").sourceSets.getByName("main").output)
}
