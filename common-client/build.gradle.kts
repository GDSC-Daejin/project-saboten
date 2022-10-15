plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.google.devtools.ksp")
}

group = "app.saboten"
version = "1.0.00"

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            languageVersion = "1.7"
        }
    }
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Saboten Common Client Module"
        homepage = "https://gdsc-dju.web.app/"
        ios.deploymentTarget = "14.0"
        framework {
            baseName = "common_client"
            isStatic = false
        }
        podfile = project.file("../ios/Podfile")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":common"))
                implementation(project(":client-paging"))

                implementation(Koin.core)
                implementation(Koin.annotation)

                implementation(Ktor.client.core)
                implementation(Ktor.client.auth)
                implementation(Ktor.client.serialization)
                implementation(Ktor.serializationKotlinx)
                implementation(Ktor.client.contentNegotiation)
                implementation(Ktor.client.json)
                implementation(Ktor.client.logging)

                implementation(KotlinX.coroutines.core)
                implementation(KotlinX.datetime)

                implementation(AndroidX.dataStore.coreOkio)
                implementation(AndroidX.dataStore.preferences.core)
            }
            kotlin.srcDirs("build/generated/ksp/commonMain/kotlin")
        }
        val commonTest by getting {
            dependencies {
                implementation(Kotlin.test)
                implementation(Kotlin.Test.common)
                implementation(Kotlin.Test.annotationsCommon)
                implementation(KotlinX.coroutines.test)
                implementation(MultiplatformSettings.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Ktor.client.cio)
                implementation(Koin.android)
                implementation(AndroidX.security.cryptoKtx)
                implementation(AndroidX.paging.commonKtx)
                implementation(project.dependencies.platform(Google.firebase.bom))
                implementation(Google.firebase.crashlyticsKtx)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(Kotlin.Test.junit5)
                implementation(project.dependencies.platform(Testing.junit.bom))
                implementation(Testing.junit.jupiter)
                implementation(Testing.junit.jupiter.api)
                implementation(Testing.junit.jupiter.params)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Ktor.client.darwin)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
                    "-Xopt-in=kotlinx.coroutines.FlowPreview",

                    "-Xopt-in=com.russhwolf.settings.ExperimentalSettingsApi",
                    "-Xopt-in=com.russhwolf.settings.ExperimentalSettingsImplementation",
                )
            }
        }
    }
    targets.getByName("android") {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi",
                    "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
                    "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",

                    "-Xopt-in=com.google.accompanist.pager.ExperimentalPagerApi",

                    "-Xopt-in=coil.annotation.ExperimentalCoilApi",
                )
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", Koin.kspCompiler)
    add("kspAndroid", Koin.kspCompiler)
    add("kspIosArm64", Koin.kspCompiler)
    add("kspIosX64", Koin.kspCompiler)
    add("kspIosSimulatorArm64", Koin.kspCompiler)
}