import org.gradle.api.publish.maven.internal.artifact.FileBasedMavenArtifact
import java.net.URI
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    ios()
    iosSimulatorArm64()

    jvm { compilations.all { kotlinOptions.jvmTarget = "11" } }


    js("web", IR) {
        useCommonJs()
        browser()
    }

    val commonMain by sourceSets.getting {
        dependencies {
            implementation(KotlinX.coroutines.core)
        }
    }

    val jvmMain by sourceSets.getting {
        dependencies {
            implementation(AndroidX.paging.commonKtx)
        }
    }

    val iosMain by sourceSets.getting
    val iosTest by sourceSets.getting
    val iosSimulatorArm64Main by sourceSets.getting
    val iosSimulatorArm64Test by sourceSets.getting

    // Set up dependencies between the source sets
    iosSimulatorArm64Main.dependsOn(iosMain)
    iosSimulatorArm64Test.dependsOn(iosTest)
}