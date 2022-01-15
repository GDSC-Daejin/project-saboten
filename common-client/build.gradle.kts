plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("native.cocoapods")
    kotlin("kapt")
    id("com.android.library")
}

group = "app.saboten"
version = "1.0.00"

kotlin {
    android()
    iosArm64("ios") {
        binaries {
            framework {
                baseName = "common"
            }
        }
    }
    cocoapods {
        summary = "Saboten Common Client Module"
        ios.deploymentTarget = "14.0"
        framework {
            baseName = "common-client"
        }
        podfile = project.file("../ios/Podfile")
    }

    js("web", IR) {
        useCommonJs()
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":common"))
                api(KotlinX.coroutines.core)
                api(Utils.inject)
                api(Koin.core)
                api(Ktor.serializationKotlinx)
                api(Ktor.client.core)
                api(Ktor.client.contentNegotiation)
                api(Ktor.client.json)
                api(Ktor.client.serialization)
                api(Ktor.client.logging)
                api(MultiplatformSettings.core)
                api(MultiplatformSettings.coroutines)
                api(MultiplatformSettings.serialization)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(Ktor.client.cio)
                api(MultiplatformSettings.datastore)
                api(AndroidX.dataStore.preferences)
                kapt(AndroidX.hilt.compiler)
                kapt(Google.dagger.hilt.compiler)
                implementation(Google.dagger.hilt.android)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Ktor.client.darwin)
            }
        }
        val iosTest by getting
        val webMain by getting {

            fun kotlinw(target: String): String =
                "org.jetbrains.kotlin-wrappers:kotlin-$target"

            dependencies {
                implementation(project.dependencies.enforcedPlatform(kotlinw("wrappers-bom:_")))
                implementation(kotlinw("react"))
                implementation(kotlinw("redux"))
            }
        }
        val webTest by getting
    }
}

android {
    compileSdk = Properties.androidTargetSDK
    defaultConfig {
        sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
        minSdk = Properties.androidMinSDK
        targetSdk = Properties.androidTargetSDK
    }
}

kotlin {
    targets.all {
        compilations.all {
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
        }
    }
}