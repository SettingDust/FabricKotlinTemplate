pluginManagement {
    repositories {
        maven {
            name = "Quilt"
            url = uri("https://maven.quiltmc.org/repository/release")
            content {
                includeGroupAndSubgroups("org.quiltmc")
            }
        }
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        mavenCentral()
        gradlePluginPortal()
    }
}

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

    // Quilt
    // https://github.com/QuiltMC/quilt-loom
    plugin("quilt-loom", "org.quiltmc.loom").version("1.4.+")

    library(
        "quilt-loader",
        "org.quiltmc",
        "quilt-loader",
    ).version("0.22.0-beta.1")

    library(
        "quilt-fabric-api",
        "org.quiltmc.quilted-fabric-api",
        "quilted-fabric-api",
    ).version("7.4.0+0.90.0-$minecraft")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
    // https://github.com/DanySK/gradle-pre-commit-git-hooks
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.1.14"
}

gitHooks {
    preCommit {
        from {
            """
            export JAVA_HOME="${System.getProperty("java.home")}"
            git diff --cached --name-only --diff-filter=ACMR | while read -r a; do echo ${'$'}(readlink -f ${"$"}a); ./gradlew spotlessApply -q -PspotlessIdeHook="${'$'}(readlink -f ${"$"}a)" </dev/null; done
            ./gradlew spotlessCheck
            """
                .trimIndent()
        }
    }
    commitMsg { conventionalCommits { defaultTypes() } }
    hook("post-commit") {
        from {
            """
            files="${'$'}(git show --pretty= --name-only | tr '\n' ' ')"
            git add ${'$'}files
            git -c core.hooksPath= commit --amend -C HEAD
            """
                .trimIndent()
        }
    }
    createHooks(true)
}

val name: String by settings

rootProject.name = name

include("mod")
include("quilt")
