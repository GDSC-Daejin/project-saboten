import de.fayard.refreshVersions.core.versionFor
import Properties as AppProperties

plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {

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
    implementation(AndroidX.core.splashscreen)

    implementation(Google.accompanist.pager)
    implementation(Google.accompanist.pager.indicators)
    implementation(Google.accompanist.systemuicontroller)
    implementation(Google.accompanist.swiperefresh)
    implementation(Google.accompanist.placeholderMaterial)
    implementation(Google.accompanist.navigation.animation)
    implementation(Google.accompanist.navigation.material)
    implementation(Google.accompanist.flowlayout)

    implementation(COIL.compose)
}

android {
    compileSdk = AppProperties.androidTargetSDK
    defaultConfig {
        minSdk = AppProperties.androidMinSDK
        targetSdk = AppProperties.androidTargetSDK
    }
    buildTypes {
        getByName("debug") {
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.ui)
    }

}