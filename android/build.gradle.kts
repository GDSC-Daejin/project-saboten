import de.fayard.refreshVersions.core.versionFor
import java.io.ByteArrayOutputStream
import java.util.Properties
import Properties as AppProperties
import kotlin.lazy

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

group = "app.saboten"
version = "1.0.00"

val gitDescribe by lazy {
    val stdout = ByteArrayOutputStream()
    rootProject.exec {
        executable("/bin/sh")
        args("-c", "git rev-parse --short HEAD")
        standardOutput = stdout
    }
    val commit = stdout.toString().trim()
    commit
}

dependencies {
    implementation(project(":common-client"))

    implementation(AndroidX.activity.ktx)
    implementation(Google.android.material)
    implementation(AndroidX.paging.compose)
    implementation(AndroidX.navigation.compose)
    implementation(AndroidX.activity.compose)

    implementation(AndroidX.compose.compiler)
    implementation(AndroidX.compose.animation)
    implementation(AndroidX.compose.material.icons.core)
    implementation(AndroidX.compose.material.icons.extended)
    implementation(AndroidX.compose.foundation)
    implementation(AndroidX.compose.runtime)
    implementation(AndroidX.compose.ui)
    implementation(AndroidX.compose.ui.tooling)

    implementation(AndroidX.constraintLayout.compose)

    implementation(COIL.compose)
    implementation(COIL.composeBase)

    implementation(Google.accompanist.insets)
    implementation(Google.accompanist.insets.ui)
    implementation(Google.accompanist.pager)
    implementation(Google.accompanist.pager.indicators)
    implementation(Google.accompanist.systemuicontroller)
    implementation(Google.accompanist.swiperefresh)
    implementation(Google.accompanist.placeholderMaterial)
    implementation(Google.accompanist.navigation.animation)
    implementation(Google.accompanist.navigation.material)
    implementation(Google.accompanist.flowlayout)

    implementation(AndroidX.core.splashscreen)
    implementation(JakeWharton.timber)

    kapt(AndroidX.paging.runtimeKtx)
    kapt(AndroidX.navigation.runtimeKtx)
    kapt(AndroidX.hilt.compiler)
    kapt(Google.dagger.hilt.compiler)
    implementation(Google.dagger.hilt.android)
    implementation(AndroidX.hilt.navigationCompose)
}

android {
    compileSdk = AppProperties.androidTargetSDK
    defaultConfig {
        applicationId = AppProperties.androidPackageName
        minSdk = AppProperties.androidMinSDK
        targetSdk = AppProperties.androidTargetSDK
        versionCode = AppProperties.androidAppVersionCode
        versionName = AppProperties.androidAppVersionName
    }
    buildTypes {
        getByName("debug") {
            versionNameSuffix = "-$gitDescribe-DEBUG"
        }
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=kotlin.OptIn",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",

            "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",

            "-Xopt-in=com.google.accompanist.pager.ExperimentalPagerApi",

            "-Xopt-in=coil.annotation.ExperimentalCoilApi",

            "-Xopt-in=com.russhwolf.settings.ExperimentalSettingsApi",
            "-Xopt-in=com.russhwolf.settings.ExperimentalSettingsImplementation",
        )
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = file("${project.rootDir.absolutePath}/keystore/debug.keystore")
            storePassword = "android"
        }

        /*
        create("release") {
            val keystoreProperties = java.util.Properties().apply {
                load(file("${project.rootDir.absolutePath}/keystore/keystore").inputStream())
            }
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
        */
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.ui)
    }

}