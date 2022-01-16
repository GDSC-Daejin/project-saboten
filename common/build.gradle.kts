import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("plugin.spring")
    kotlin("native.cocoapods")
    kotlin("kapt")
    id("com.android.library")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
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

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
        else -> ::iosX64
    }

    iosTarget("ios") {}

    cocoapods {
        summary = "Saboten Common Module"
        homepage = "https://gdsc-dju.web.app/"
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
                api(KotlinX.serialization.json)
                implementation(KotlinX.coroutines.core)
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
                api(JakeWharton.timber)

                api(AndroidX.compose.compiler)
                api(AndroidX.compose.runtime)
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


kotlin {

    targets.withType(KotlinNativeTarget::class.java) {
        binaries.all {
            binaryOptions["memoryModel"] = "experimental"
        }
    }

}