plugins {
    id("com.android.application") version "7.0.4" apply false
    id("com.android.library") version "7.0.4" apply false

    val kotlinVersion = "1.6.10"
    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("android") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion  apply false
    kotlin("plugin.jpa") version kotlinVersion  apply false

    id("org.springframework.boot") version "2.5.6" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"  apply false
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(Google.dagger.hilt.android.gradlePlugin)
        classpath(Google.firebase.appDistributionGradlePlugin)
    }
}

group = "app.saboten"
version = "1.0.00"