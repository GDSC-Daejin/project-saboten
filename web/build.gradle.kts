import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.*

plugins {
    kotlin("js")
    kotlin("plugin.serialization")
    id("com.bnorm.react.kotlin-react-function") version "0.7.0"
}

group = "app.saboten"
version = "1.0.00"

fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

dependencies {
    implementation(enforcedPlatform(kotlinw("wrappers-bom:_")))

    testImplementation(kotlin("test"))
    implementation(project(":common-client"))

    implementation(kotlinw("redux"))
    implementation(kotlinw("react"))
    implementation(kotlinw("react-dom"))
    implementation(kotlinw("react-redux"))
    implementation(kotlinw("react-router-dom"))
    implementation(kotlinw("styled-next"))

    implementation(npm("react", "17.0.2"))
    implementation(npm("react-dom", "17.0.2"))
    implementation(npm("react-router-dom", "6.2.1"))

    implementation(npm("styled-components", "5.3.3"))

}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                outputFileName = "app.js"
                cssSupport.enabled = true
                mode = if(project.hasProperty("prod")) Mode.PRODUCTION else Mode.DEVELOPMENT
            }
            useCommonJs()
        }
    }
}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().nodeVersion = "16.0.0"
}