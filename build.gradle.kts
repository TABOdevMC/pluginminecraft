plugins {
    java
}

group = "fr.tabo"
version = "1.0.0"

repositories {
    mavenCentral()

    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        name = "multiverse"
        url = uri("https://repo.onarandombox.com/content/groups/public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.+")

    // Installed on the server, never bundled into our JAR.
    compileOnly("net.luckperms:api:5.5")
    compileOnly("org.mvplugins.multiverse.core:multiverse-core:5.8.1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(25)
    }

    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }

    jar {
        archiveBaseName.set("PluginMinecraft")
    }
}
