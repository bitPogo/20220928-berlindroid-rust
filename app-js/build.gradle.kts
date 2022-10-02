/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.*
import tech.antibytes.gradle.dependency.Dependency
import io.bitpogo.gradle.rustkmp.dependency.Dependency as LocalDependency


plugins {
    kotlin("js")


    id("tech.antibytes.kmock.kmock-gradle")
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                mode = if(project.hasProperty("prod")) {
                    Mode.PRODUCTION
                } else {
                    Mode.DEVELOPMENT
                }
            }
        }
        useCommonJs()
    }
}

kmock {
    rootPackage = "io.bitpogo.rustkmp.js"
}

dependencies {
    implementation(enforcedPlatform(LocalDependency.js.kotlinWrapper.bom))
    implementation(LocalDependency.js.kotlinWrapper.react)
    implementation(LocalDependency.js.kotlinWrapper.reactDom)
    implementation(LocalDependency.js.kotlinWrapper.extension)
    implementation(Dependency.multiplatform.kotlin.js)
    implementation(Dependency.multiplatform.coroutines.js)
    implementation(devNpm("copy-webpack-plugin", "11.0.0"))
    implementation(
        npm(
            "bigint_arithmetic",
            File("${rootProject.projectDir.absolutePath.trimEnd('/')}/bigint/wasm")
        )
    )
    implementation(project(":bigint"))


    testImplementation(Dependency.multiplatform.test.common)
    testImplementation(Dependency.multiplatform.test.js)
    testImplementation(Dependency.multiplatform.test.annotations)
    testImplementation(LocalDependency.antibytes.test.js.core)
    testImplementation(LocalDependency.antibytes.test.js.coroutine)
    testImplementation(LocalDependency.antibytes.test.js.annotations)
    testImplementation(LocalDependency.antibytes.test.js.fixture)
    testImplementation(LocalDependency.antibytes.test.js.kmock)
    testImplementation(Dependency.multiplatform.test.coroutines)
    testImplementation(Dependency.js.test.js)
}
