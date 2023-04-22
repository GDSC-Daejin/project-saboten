pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }

    plugins {
        id("de.fayard.refreshVersions") version "0.50.2"
////                                # available:"0.51.0"
    }
}

plugins {
    id("de.fayard.refreshVersions")
}