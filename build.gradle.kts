plugins {
    `java-library`
    id("net.neoforged.moddev") version "2.0.134"
}

val modId = providers.gradleProperty("mod_id").get()
val neoForgeVersion = providers.gradleProperty("neo_version").get()
val parchmentMinecraftVersion = providers.gradleProperty("parchment_minecraft_version").get()
val parchmentMappingsVersion = providers.gradleProperty("parchment_mappings_version").get()

group = providers.gradleProperty("mod_group_id").get()
version = providers.gradleProperty("mod_version").get()

base {
    archivesName.set(modId)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()

    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
    }

    maven {
        name = "SpongePowered"
        url = uri("https://repo.spongepowered.org/repository/maven-public/")
    }
}

val localRuntime by configurations.creating

configurations.named("runtimeClasspath") {
    extendsFrom(localRuntime)
}

neoForge {
    version = neoForgeVersion

    parchment {
        mappingsVersion.set(parchmentMappingsVersion)
        minecraftVersion.set(parchmentMinecraftVersion)
    }

    runs {
        create("client") {
            client()
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        create("server") {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        create("gameTestServer") {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.INFO
        }
    }

    mods {
        register(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

dependencies {
    compileOnly("maven.modrinth:vu3NZ5Ma:8HHhJt6i")
    add(localRuntime.name, "maven.modrinth:vu3NZ5Ma:8HHhJt6i")
    compileOnly("org.spongepowered:mixin:0.8.7")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(21)
}
