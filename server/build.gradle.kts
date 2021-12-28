import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
}

group = "app.saboten"
version = "1.0.00"

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":common"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("app.saboten.server.ServerKt")
}