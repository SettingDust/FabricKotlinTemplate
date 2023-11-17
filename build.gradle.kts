plugins {
    idea
    java

    alias(catalog.plugins.spotless)

    alias(catalog.plugins.semver)
}

group = "settingdust.template"

version = semver.semVersion.toString()

allprojects {
    repositories {
        mavenCentral()
    }
}

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

    kotlin { ktfmt().kotlinlangStyle() }

    kotlinGradle { ktfmt().kotlinlangStyle() }
}
