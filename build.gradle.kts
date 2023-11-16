plugins {
    idea
    alias(catalog.plugins.fabric.loom)

    alias(catalog.plugins.kotlin.jvm)
    alias(catalog.plugins.kotlin.plugin.serialization)

    alias(catalog.plugins.spotless)

    alias(catalog.plugins.semver)
    alias(catalog.plugins.conventional.commits)
}

group = "settingdust.template"

version = semver.version

val id by project.properties
val name by project.properties

repositories {
    maven("https://maven.terraformersmc.com/releases")
    mavenCentral()
}

dependencies {
    minecraft(catalog.minecraft)
    mappings(variantOf(catalog.yarn) { classifier("v2") })

    modImplementation(catalog.fabric.loader)
    modImplementation(catalog.fabric.api)
    modImplementation(catalog.fabric.kotlin)

    modImplementation(catalog.modmenu)
}

loom { splitEnvironmentSourceSets() }

kotlin { jvmToolchain(17) }

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

spotless {
    java { palantirJavaFormat() }

    kotlin { ktfmt().kotlinlangStyle() }

    kotlinGradle { ktfmt().kotlinlangStyle() }

    json {
        target("src/**/*.json")
        gson().indentWithSpaces(2)
    }
}

val metadata =
    mapOf(
        "group" to group,
        "id" to id,
        "name" to name,
        "version" to version,
        "source" to "https://github.com/SettingDust/FabricKotlinTemplate",
        "minecraft" to "~1.20",
        "fabric-loader" to "~0.14",
        "fabric-kotlin" to ">=1.10",
        "modmenu" to ">=5"
    )

tasks {
    processResources {
        inputs.properties(metadata)
        filesMatching("fabric.mod.json") { expand(metadata) }
    }

    jar { from("LICENSE") }
}
