import io.bitpogo.gradle.rustkmp.dependency.Dependency
import io.bitpogo.gradle.rustkmp.dependency.addCustomRepositories

plugins {
    `kotlin-dsl`

    id("io.bitpogo.gradle.rustkmp.dependency")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    addCustomRepositories()
}

dependencies {
    implementation(Dependency.gradle.dependency)
    implementation(Dependency.gradle.serialization)
    implementation(Dependency.gradle.coverage)
    implementation(Dependency.gradle.spotless)
    implementation(Dependency.gradle.projectConfig)
    implementation(Dependency.gradle.runtimeConfig)
    implementation(Dependency.gradle.sqldelight)
    implementation(Dependency.gradle.hilt)
    implementation(Dependency.gradle.kmock)
    implementation(Dependency.gradle.rustAndroid)
}
