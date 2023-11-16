pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0" }

dependencyResolutionManagement.versionCatalogs.create("catalog") {
    // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
    val kotlin = "1.9.20"
    version("kotlin", kotlin)
    plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").version(kotlin)
    plugin("kotlin-plugin-serialization", "org.jetbrains.kotlin.plugin.serialization")
        .version(kotlin)

    // https://plugins.gradle.org/plugin/com.diffplug.spotless
    plugin("spotless", "com.diffplug.spotless").version("6.22.0")

    // https://github.com/jmongard/Git.SemVersioning.Gradle
    plugin("semver", "com.github.jmongard.git-semver-plugin").version("0.10.1")

    // https://github.com/nicolasfara/conventional-commits
    plugin("conventional-commits", "it.nicolasfarabegoli.conventional-commits").version("3.1.3")

    // https://fabricmc.net/develop/
    plugin("fabric-loom", "fabric-loom").version("1.4.+")

    val minecraft = "1.20.1"
    version("minecraft", minecraft)
    library("minecraft", "com.mojang", "minecraft").version(minecraft)

    library("yarn", "net.fabricmc", "yarn").version("$minecraft+build.10")

    library("fabric-loader", "net.fabricmc", "fabric-loader").version("0.14.24")

    library("fabric-api", "net.fabricmc.fabric-api", "fabric-api").version("0.90.7+$minecraft")

    // https://modrinth.com/mod/fabric-language-kotlin/versions
    library(
            "fabric-kotlin",
            "net.fabricmc",
            "fabric-language-kotlin",
        )
        .version("1.10.13+kotlin.$kotlin")

    library("modmenu", "com.terraformersmc", "modmenu").version("7.2.2")
}

val name: String by settings

rootProject.name = name
