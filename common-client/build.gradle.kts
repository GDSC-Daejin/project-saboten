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
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {

            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13")
            }
        }
        val iosMain by getting {
            dependencies {
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