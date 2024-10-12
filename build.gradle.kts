plugins {
    id("java")
    id("fabric-loom") version("1.7.3") apply false
}

val MOD_ID by extra { "vegandelight" }
val MOD_VERSION by extra { "1.1.1" }
val MOD_NAME by extra { "Vegan Delight" }

val MINECRAFT_VERSION by extra { "1.20.4" }
val MINECRAFT_VERSION_RANGE by extra { "1.20.4" }
val PARCHMENT_VERSION by extra { "2024.04.14" }

val NEOFORGE_VERSION by extra { "20.4.237" }
val NEOFORGE_VERSION_RANGE by extra { "*" }
val FD_NEO_VERSION by extra { "" }

val FDRF_VERSION by extra { "1.20.1-2.1.6+refabricated" }
val FABRIC_LOADER_VERSION by extra { "0.16.7" }
val FABRIC_API_VERSION by extra { "0.97.2+1.20.4" }


allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.jar {
    enabled = false
}
