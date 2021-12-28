import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.*

plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

group = "app.saboten"
version = "1.0.00"

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":common-client"))

    implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:_")

    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:_")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:_")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:_")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:_")

    implementation(npm("react", "17.0.2"))
    implementation(npm("react-dom", "17.0.2"))

    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:_")
    implementation(npm("styled-components", "~5.3.3"))
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
                mode = if(project.hasProperty("prod")) Mode.PRODUCTION else Mode.DEVELOPMENT
            }
            useCommonJs()
        }
    }
}