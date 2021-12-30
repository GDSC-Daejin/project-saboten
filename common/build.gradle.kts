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

    jvm("server")

    iosArm64("ios") {
        binaries {
            framework {
                baseName = "common"
            }
        }
    }

    cocoapods {
        summary = "Saboten Common Module"
        ios.deploymentTarget = "14.0"
        framework {
            baseName = "common"
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
                api(KotlinX.serialization.core)
                api(KotlinX.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(AndroidX.appCompat)
                api(AndroidX.core.ktx)
                api(AndroidX.lifecycle.viewModelKtx)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13")
            }
        }
        val serverMain by getting {
            dependencies {
            }
        }
        val serverTest by getting
        val iosMain by getting {
            dependencies {
            }
        }
        val iosTest by getting
        val webMain by getting {
            dependencies {
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