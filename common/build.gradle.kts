import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("native.cocoapods")
    id("com.android.library")
}

group = "app.saboten"
version = "1.0.00"

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

kotlin {
    android()
    jvm("server")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Saboten Common Module"
        homepage = "https://gdsc-dju.web.app/"
        ios.deploymentTarget = "14.0"
        framework {
            baseName = "common"
            isStatic = false
        }
        podfile = project.file("../ios/Podfile")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(KotlinX.coroutines.core)
                implementation(KotlinX.serialization.core)
            }
        }
        val commonTest by getting {
            dependencies {
            }
        }
        val androidMain by getting {
            dependencies {
                api(AndroidX.core.ktx)
                api(AndroidX.lifecycle.viewModelKtx)
                api(JakeWharton.timber)
            }
        }
        val androidTest by getting {
            dependencies {
            }
        }
        val serverMain by getting {
            dependencies {
            }
        }
        val serverTest by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
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