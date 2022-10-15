import de.fayard.refreshVersions.core.versionFor
import java.util.Properties
import Properties as AppProperties

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    id("com.google.devtools.ksp")
}

group = "app.saboten"
version = AppProperties.androidAppVersionName

fun createDebugReleaseNote(): String {
    val releaseNote = File("${project.rootDir}/fastlane/metadata/android/ko-KR/changelogs/debug-release-notes.txt")
    releaseNote.writeText(
        "Version: \n\tVer ${AppProperties.androidAppVersionName}-$gitDescribe-DEBUG\n\n" +
                "Branch\n\t$gitBranch\n\n" +
                "Developer\n\t$developer\n\n" +
                "Project Development Overview\n" +
                "\tTask\n\t\t$commitList"
    )
    releaseNote.createNewFile()
    return releaseNote.absolutePath
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-client"))
    implementation(project(":client-paging"))
    implementation(project(":android-ui"))

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
    implementation(COIL.svg)

    implementation(Orbit.core)
    implementation(Orbit.compose)
    implementation(Orbit.viewmodel)

    implementation(Google.accompanist.pager)
    implementation(Google.accompanist.pager.indicators)
    implementation(Google.accompanist.systemUiController)
    implementation(Google.accompanist.swipeRefresh)
    implementation(Google.accompanist.placeholderMaterial)
    implementation(Google.accompanist.navigation.animation)
    implementation(Google.accompanist.navigation.material)
    implementation(Google.accompanist.flowLayout)

    implementation(platform(Google.firebase.bom))
    implementation(Google.firebase.analyticsKtx)
    implementation(Google.firebase.crashlyticsKtx)
    implementation(Google.firebase.performanceMonitoring)

    implementation(AndroidX.core.splashscreen)
    implementation(JakeWharton.timber)

    implementation(AndroidX.paging.runtimeKtx)
    implementation(AndroidX.navigation.runtimeKtx)

    implementation(Koin.android)
    implementation(Koin.annotation)
//    implementation(Koin.compose)
    implementation("io.insert-koin:koin-androidx-compose:3.2.1")
    ksp(Koin.kspCompiler)

    implementation(ComposeDestination.core)
    implementation(ComposeDestination.animationsCore)
    ksp(ComposeDestination.ksp)

    testImplementation(Kotlin.test)
    testImplementation(Kotlin.Test.junit5)
    testImplementation(platform(Testing.junit.bom))
    testImplementation(Testing.junit.jupiter.api)
    testImplementation(Testing.junit.jupiter.params)
}

android {
    compileSdk = AppProperties.androidTargetSDK
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        applicationId = AppProperties.androidPackageName
        minSdk = AppProperties.androidMinSDK
        targetSdk = AppProperties.androidTargetSDK
        versionCode = AppProperties.androidAppVersionCode
        versionName = AppProperties.androidAppVersionName
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = file("${project.rootDir.absolutePath}/keystore/debug.keystore")
            storePassword = "android"
        }

        create("release") {
            val keystoreProperties = Properties().apply {
                load(file("${project.rootDir.absolutePath}/keystore/keystore").inputStream())
            }
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {
        getByName("debug") {
            createDebugReleaseNote()
            versionNameSuffix = "-$gitDescribe-DEBUG"
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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

    bundle {
        bundle {
            language {
                enableSplit = false
            }
            density {
                enableSplit = true
            }
            abi {
                enableSplit = true
            }
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.compiler)
    }

    applicationVariants.all {
        outputs.all {
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName = outputFileName
                    .replace("app-release", "saboten-android-${AppProperties.androidAppVersionName}")
                    .replace("app-debug", "saboten-android-${AppProperties.androidAppVersionName}")
        }
//        val variantName = name
//        sourceSets {
//            getByName("main") {
//                java.srcDir(File("build/generated/ksp/$variantName/kotlin"))
//            }
//        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}