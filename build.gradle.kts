val MOD_ID by extra { "vegandelight" }
val MOD_VERSION by extra { "1.1.1" }
val MOD_NAME by extra { "Vegan Delight" }

val MINECRAFT_VERSION by extra { "1.21.1" }
val MINECRAFT_VERSION_RANGE by extra { "[1.21,)" }
val PARCHMENT_MC_VERSION by extra { "1.21" }
val PARCHMENT_VERSION by extra { "2024.07.28" }

val NEOFORGE_VERSION by extra { "21.1.68" }
val NEOFORGE_VERSION_RANGE by extra { "*" }
val FD_NEO_VERSION by extra { "1.21.1-1.2.4a" }

val FDRF_VERSION by extra { "1.21-2.1.9+refabricated" }
val FABRIC_LOADER_VERSION by extra { "0.16.7" }
val FABRIC_API_VERSION by extra { "0.105.0+1.21.1" }

plugins {
    id("java")
    id("fabric-loom") version "1.7.3" apply false
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        jar {
            // put all built jars in the same dir (build/libs)
            destinationDirectory = rootDir.resolve("build").resolve("libs")

            // add license file to jars
            from(rootDir.resolve("LICENSE.md"))

            // required because apparently some classes are duplicated
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }

    version = MOD_VERSION
    group = "net.player005.vegandelightfabric"

    base {
        archivesName = "vegan-delight-${project.name}-${MINECRAFT_VERSION}"
    }
}

tasks.jar {
    enabled = false
}
