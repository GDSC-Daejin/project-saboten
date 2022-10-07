plugins {
    id("com.android.application") version "7.0.4" apply false
    id("com.android.library") version "7.0.4" apply false

    val kotlinVersion = "1.7.10"
    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("android") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false

    id("org.springframework.boot") version "2.5.6" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("com.google.devtools.ksp") version "1.7.10-1.0.6" apply false
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(Google.dagger.hilt.android.gradlePlugin)
        classpath(Google.playServicesGradlePlugin)
        classpath(Google.firebase.appDistributionGradlePlugin)
        classpath(Google.firebase.crashlyticsGradlePlugin)
        classpath(Google.firebase.performanceMonitoringGradlePlugin)
    }
}

subprojects {
    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
        }
    }
}

group = "app.saboten"
version = "1.0.00"