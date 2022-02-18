plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.serialization")
    war
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "app.saboten"
version = "1.0.00"

dependencies {
    implementation(project(":common"))

    implementation(KotlinX.serialization.core)
    implementation(KotlinX.coroutines.core)

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // 임시
    implementation("org.postgresql:postgresql")

    implementation(SpringFox.swagger3)
    implementation(Jpa.jpa)
    implementation(Jpa.lombok)

    testImplementation(Test.bootStatertest)
    testImplementation(Test.mockk)
    //testImplementation(kotlin("test"))
}
repositories {
    mavenCentral()
}

//tasks.test {
//    useJUnitPlatform()
//}

tasks.withType<org.gradle.api.tasks.testing.Test> {
    useJUnitPlatform()
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

tasks.processResources {
    dependsOn(":web:browserWebpack")
    from(project(":web").projectDir.resolve("src/main/resources")) {
        into("static")
    }
    from(project(":web").buildDir.resolve("distributions/app.js")) {
        into("static")
    }
}