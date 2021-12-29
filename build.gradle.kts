plugins {
    id("com.android.application") version "7.0.4" apply false
    id("com.android.library") version "7.0.4" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10" apply false
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(Google.dagger.hilt.android.gradlePlugin)
    }
}

group = "app.saboten"
version = "1.0.00"