@file:Suppress("unused", "UnstableApiUsage")

object Koin {

    const val core = "io.insert-koin:koin-core:_"

}

object MultiplatformSettings {
    const val core = "com.russhwolf:multiplatform-settings:_"

    const val serialization = "com.russhwolf:multiplatform-settings-serialization:_"

    const val datastore = "com.russhwolf:multiplatform-settings-datastore:_"

    const val test = "com.russhwolf:multiplatform-settings-test:_"

    const val coroutines = "com.russhwolf:multiplatform-settings-coroutines:_"
}

object Multiplatform {
    const val auth = "dev.gitlive:firebase-auth:_"
    const val firestore = "dev.gitlive:firebase-firestore:_"
}

val Ktor.Client.contentNegotiation get() = "io.ktor:ktor-client-content-negotiation:_"
val Ktor.serializationKotlinx get() = "io.ktor:ktor-serialization-kotlinx:_"

val Firebase.multiplatform get() = Multiplatform

val AndroidX.glanceAppWidget get() = "androidx.glance:glance-appwidget:_"

val Google.Android.maps get() = "com.google.maps.android:maps-ktx:_"
val Google.Android.mapsUtils get() = "com.google.maps.android:maps-utils-ktx:_"
val Google.Accompanist.navigation get() = Navigation
val Google.Accompanist.placeholderMaterial get() = "com.google.accompanist:accompanist-placeholder-material:_"

object Navigation {
    const val animation = "com.google.accompanist:accompanist-navigation-animation:_"
    const val material = "com.google.accompanist:accompanist-navigation-material:_"
}

object Mokk {
    const val core = "io.mockk:mockk:_"
    const val common = "io.mockk:mockk-common:_"
}

object Utils {

    const val paging = "io.github.kuuuurt:multiplatform-paging:_"

    const val kotlinxDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:_"

    const val mokoParcel = "dev.icerock.moko:parcelize:_"

    const val mokoResource = "dev.icerock.moko:resources:_"

    const val mokoResourcePlugins = "dev.icerock.moko:resources-generator:_"

    const val imageCompressor = "id.zelory:compressor:_"

    const val composeCollapsingToolbarLayout = "me.onebone:toolbar-compose:_"

    const val jsoup = "org.jsoup:jsoup:_"

    const val markdown = "io.noties.markwon:core:_"
    const val markdownStrikethrough = "io.noties.markwon:ext-strikethrough:_"
    const val markdownTables = "io.noties.markwon:ext-tables:_"
    const val markdownHtml = "io.noties.markwon:html:_"
    const val markdownCoil = "io.noties.markwon:image-coil:_"
    const val markdownLinkify = "io.noties.markwon:linkify:_"

    const val qrGenerator = "com.google.zxing:zxingorg:_"

    const val inject = "com.chrynan.inject:inject:_"
}

object ComposeDestination {
    const val core = "io.github.raamcosta.compose-destinations:core:_"
    const val animationsCore = "io.github.raamcosta.compose-destinations:animations-core:_"
    const val ksp = "io.github.raamcosta.compose-destinations:ksp:_"
}