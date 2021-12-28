pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

}

plugins {
    id("de.fayard.refreshVersions") version "0.30.1"
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://repo.repsy.io/mvn/chrynan/public")
    }
}

rootProject.name = "saboten-service"


include(":android")
include(":common")
include(":common-client")
include(":web")
include(":server")
