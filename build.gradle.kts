plugins {
    idea
    alias(catalog.plugins.idea.ext)

    java

    alias(catalog.plugins.spotless)

    alias(catalog.plugins.semver)
}

group = "settingdust.template"

version = semver.semVersion.toString()

allprojects { repositories { mavenCentral() } }

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        maven("https://maven.terraformersmc.com/releases") {
            content { includeGroup("com.terraformersmc") }
        }
    }
}

spotless {
    java { palantirJavaFormat() }

    kotlin {
        target("**/src/**/*.kt")
        ktfmt().kotlinlangStyle()
    }

    kotlinGradle {
        target("**/*.gradle.kts")
        ktfmt().kotlinlangStyle()
    }
}

// idea.project.settings.runConfigurations {
//    create("Quilt Client", org.jetbrains.gradle.ext.Gradle::class.java) {
//        taskNames = listOf(":quilt:runClient")
//    }
//
//    create("Quilt Server", org.jetbrains.gradle.ext.Gradle::class.java) {
//        taskNames = listOf(":quilt:runServer")
//    }
// }
