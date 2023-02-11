pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

}

plugins {
    id("de.fayard.refreshVersions") version "0.50.2"
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        maven(url = "https://repo.repsy.io/mvn/chrynan/public")
    }
}

rootProject.name = "saboten-service"


include(":common")
include(":common-client")

include(":android")
include(":server")
include(":android-ui")
include(":android-ui-samples")
