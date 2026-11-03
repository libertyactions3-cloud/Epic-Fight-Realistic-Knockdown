pluginManagement {
    repositories {
        gradlePluginPortal()

        maven("https://maven.neoforged.net/releases") {
            content {
                includeGroup("net.neoforged")
            }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
}

rootProject.name = "Epic Fight Realistic Knockdown"
