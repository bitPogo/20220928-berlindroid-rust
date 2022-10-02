import tech.antibytes.gradle.dependency.Dependency
import io.bitpogo.gradle.rustkmp.dependency.Dependency as LocalDependency
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import tech.antibytes.gradle.configuration.isIdea

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")

    id("kotlin-parcelize")

    id("tech.antibytes.gradle.configuration")
    id("tech.antibytes.gradle.coverage")
    id("tech.antibytes.kmock.kmock-gradle")

    id("org.mozilla.rust-android-gradle.rust-android")
}

val rustDir = "${projectDir.absolutePath.trimEnd('/')}/rsrc"
val jvmRustLib = "$buildDir/generated/rust/jvm"
val wasmRustLib = "$projectDir/wasm"
val platform = "osx_arm64" // TODO

kotlin {
    android()

    jvm()

    js(IR) {
        compilations {
            this.forEach {
                it.compileKotlinTask.kotlinOptions.sourceMap = true
                it.compileKotlinTask.kotlinOptions.metaInfo = true

                if (it.name == "main") {
                    it.compileKotlinTask.kotlinOptions.main = "call"
                }
            }
        }

        browser {
            testTask {
                useKarma {
                    useChromeHeadlessNoSandbox()
                }
            }
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.ExperimentalUnsignedTypes")
                optIn("kotlin.RequiresOptIn")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.common)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Dependency.multiplatform.test.common)
                implementation(Dependency.multiplatform.test.annotations)

                implementation(LocalDependency.antibytes.test.kmp.core)
                implementation(LocalDependency.antibytes.test.kmp.annotations)
                implementation(LocalDependency.antibytes.test.kmp.fixture)
                implementation(LocalDependency.antibytes.test.kmp.kmock)
            }
        }

        val concurrentMain by creating {
            dependsOn(commonMain)
        }

        val concurrentTest by creating {
            dependsOn(commonTest)
        }

        val androidMain by getting {
            dependsOn(concurrentMain)

            dependencies {
                implementation(Dependency.multiplatform.kotlin.android)
            }
        }
        if (!isIdea()) {
            val androidAndroidTestRelease by getting {
                dependsOn(concurrentTest)
            }
            val androidAndroidTest by getting {
                dependsOn(concurrentTest)
                dependsOn(androidAndroidTestRelease)

            }
            val androidTestFixturesDebug by getting {
                dependsOn(concurrentTest)
            }
            val androidTestFixturesRelease by getting {
                dependsOn(concurrentTest)
            }

            val androidTestFixtures by getting {
                dependsOn(concurrentTest)
                dependsOn(androidTestFixturesDebug)
                dependsOn(androidTestFixturesRelease)
            }

            val androidTest by getting {
                dependsOn(androidTestFixtures)
            }
        }
        val androidTest by getting {
            dependsOn(concurrentTest)

            dependencies {
                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
                implementation(Dependency.android.test.robolectric)
            }
        }

        val androidAndroidTest by getting {
            dependsOn(concurrentTest)
            dependencies {
                implementation(Dependency.jvm.test.junit)
                implementation(Dependency.android.test.junit)
                implementation(Dependency.android.test.espressoCore)
                implementation(Dependency.android.test.uiAutomator)
            }
        }

        val jvmMain by getting {
            dependsOn(concurrentMain)
            resources.srcDir(jvmRustLib)

            dependencies {
                implementation(Dependency.multiplatform.kotlin.jdk8)
                implementation(LocalDependency.jvm.nativeBundler)
                implementation(LocalDependency.jvm.slf4jNop)
                implementation(LocalDependency.jvm.slf4jApi)
            }
        }
        val jvmTest by getting {
            dependsOn(concurrentTest)
            dependencies {
                implementation(Dependency.multiplatform.test.jvm)
                implementation(Dependency.multiplatform.test.junit)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(Dependency.multiplatform.kotlin.js)
                implementation(Dependency.js.nodejs)
                implementation(Dependency.multiplatform.coroutines.js)
                implementation(devNpm("copy-webpack-plugin", "11.0.0"))
                implementation(npm("bigint_arithmetic", File(wasmRustLib)))

            }
        }

        val jsTest by getting {
            dependencies {
                implementation(Dependency.multiplatform.test.js)
                implementation(Dependency.multiplatform.test.coroutines)
            }
        }
    }
}

android {
    ndkVersion = "25.1.8937393"

    namespace = "io.bitpogo.rustkmp.bignumber"

    defaultConfig {
        ndk.abiFilters.addAll(
            listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        )
    }
}

val userHome = project.findProperty("gradle.user.home") as String?

cargo {
    prebuiltToolchains = true
    module  = rustDir
    libname = "bigint_arithmetic"
    targets = listOf(
        "arm",
        "arm64",
        "x86",
        "x86_64",
    )
    targetIncludes = arrayOf("*.*")
    pythonCommand = "python3"

    if (userHome != null) {
        cargoCommand = "$userHome/.cargo/bin/cargo"
        rustcCommand = "$userHome/.cargo/bin/rustc"
    }
}


kmock {
    rootPackage = "io.bitpogo.rustkmp.bignumber"
    freezeOnDefault = false
}

val cargoJvmAssemble by tasks.creating(Exec::class.java) {
    workingDir = File(rustDir)
    val cargo = if (userHome != null) {
        "$userHome/.cargo/bin/cargo"
    } else {
        "cargo"
    }

    commandLine(cargo, "build", "--release")
}
val cargoJvmBuild by tasks.creating(Sync::class.java) {
    group = "Rust"
    dependsOn(cargoJvmAssemble)

    from("$rustDir/target/release")
    include("*.dylib","*.so","*.dll")
    into( "$jvmRustLib/natives/$platform")
}

tasks.named("jvmProcessResources") {
    dependsOn(cargoJvmBuild)
}

tasks.named("jvmTest", Test::class.java) {
    systemProperty("java.library.path", jvmRustLib)
}

val cargoWasmAssemble by tasks.creating(Exec::class.java) {
    workingDir = File(rustDir)
    val cargo = if (userHome != null) {
        "$userHome/.cargo/bin/wasm-pack"
    } else {
        "wasm-pack"
    }

    commandLine(cargo, "build", "--target", "web")
}

val cargoWasmBuild by tasks.creating(Sync::class.java) {
    group = "Rust"
    dependsOn(cargoWasmAssemble)

    from("$rustDir/pkg")
    include("*.ts","*.js","*.wasm", "*.json")
    into(wasmRustLib)
}

tasks.withType(KotlinCompile::class.java) {
    dependsOn("cargoBuild", cargoJvmBuild)
}

tasks.withType(KotlinJsCompile::class.java) {
     dependsOn(cargoWasmBuild)
}

tasks.named("clean") {
    doLast {
        File("$rustDir/target").deleteRecursively()
        File("$rustDir/pkg").deleteRecursively()
    }
}
