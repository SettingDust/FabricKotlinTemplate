plugins {
    alias(catalog.plugins.quilt.loom)
}

val id: String by rootProject.properties

loom {
    mods {
        register(id) {
            modFiles.from(project(":mod").tasks.named("remapJar"))
        }
    }

    runs {
        named("client") {
            ideConfigGenerated(true)
            name("Quilt Client")
        }

        named("server") {
            ideConfigGenerated(true)
            name("Quilt Server")
        }
    }
}

repositories {
    maven {
        name = "Quilt"
        url = uri("https://maven.quiltmc.org/repository/release")
        content {
            includeGroupAndSubgroups("org.quiltmc")
        }
    }
}

dependencies {
    minecraft(catalog.minecraft)
    mappings(variantOf(catalog.yarn) { classifier("v2") })

    modRuntimeOnly(catalog.quilt.loader)
    modRuntimeOnly(catalog.quilt.fabric.api)
    modRuntimeOnly(catalog.fabric.kotlin) {
        exclude(module = "fabric-loader")
    }

    modRuntimeOnly(catalog.modmenu) {
        exclude(module = "fabric-loader")
    }
}

tasks {
    ideaSyncTask {
        enabled = true
    }
}
