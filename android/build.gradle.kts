plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

group = "app.saboten"
version = "1.0.00"

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
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "app.saboten.android"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}