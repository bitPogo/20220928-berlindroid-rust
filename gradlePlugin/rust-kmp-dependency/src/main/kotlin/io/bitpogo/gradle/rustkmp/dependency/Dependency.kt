/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.gradle.rustkmp.dependency

object Dependency {
    val gradle = GradlePlugin
    val antibytes = AntiBytes

    object AntiBytes {
        val test = Test

        object Test {
            val kmp = KmpTest

            object KmpTest {
                const val annotations = "tech.antibytes.test-utils-kmp:test-utils-annotations:${Version.Antibytes.test}"
                const val core = "tech.antibytes.test-utils-kmp:test-utils:${Version.Antibytes.test}"
                const val fixture = "tech.antibytes.kfixture:core:${Version.Antibytes.kfixture}"
                const val coroutine = "tech.antibytes.test-utils-kmp:test-utils-coroutine:${Version.Antibytes.test}"
                const val ktor = "tech.antibytes.test-utils-kmp:test-utils-ktor:${Version.Antibytes.test}"
                const val kmock = "tech.antibytes.kmock:kmock:${Version.Antibytes.kmock}"
            }

            val jvm = JvmTest

            object JvmTest {
                const val annotations = "tech.antibytes.test-utils-kmp:test-utils-annotations-jvm:${Version.Antibytes.test}"
                const val core = "tech.antibytes.test-utils-kmp:test-utils-jvm:${Version.Antibytes.test}"
                const val fixture = "tech.antibytes.kfixture:core-jvm:${Version.Antibytes.kfixture}"
                const val coroutine = "tech.antibytes.test-utils-kmp:test-utils-coroutine-jvm:${Version.Antibytes.test}"
                const val ktor = "tech.antibytes.test-utils-kmp:test-utils-ktor-jvm:${Version.Antibytes.test}"
                const val kmock = "tech.antibytes.kmock:kmock-jvm:${Version.Antibytes.kmock}"
            }

            val js = JsTest

            object JsTest {
                const val annotations = "tech.antibytes.test-utils-kmp:test-utils-annotations-js:${Version.Antibytes.test}"
                const val core = "tech.antibytes.test-utils-kmp:test-utils-js:${Version.Antibytes.test}"
                const val fixture = "tech.antibytes.kfixture:core-js:${Version.Antibytes.kfixture}"
                const val coroutine = "tech.antibytes.test-utils-kmp:test-utils-coroutine-js:${Version.Antibytes.test}"
                const val ktor = "tech.antibytes.test-utils-kmp:test-utils-ktor-js:${Version.Antibytes.test}"
                const val kmock = "tech.antibytes.kmock:kmock-js:${Version.Antibytes.kmock}"
            }

            val android = AndroidTest

            object AndroidTest {
                const val annotations = "tech.antibytes.test-utils-kmp:test-utils-annotations-android:${Version.Antibytes.test}"
                const val core = "tech.antibytes.test-utils-kmp:test-utils-android:${Version.Antibytes.test}"
                const val fixture = "tech.antibytes.kfixture:core-android:${Version.Antibytes.kfixture}"
                const val coroutine = "tech.antibytes.test-utils-kmp:test-utils-coroutine-android:${Version.Antibytes.test}"
                const val ktor = "tech.antibytes.test-utils-kmp:test-utils-ktor-android:${Version.Antibytes.test}"
                const val kmock = "tech.antibytes.kmock:kmock-android:${Version.Antibytes.kmock}"
            }
        }
    }

    val jvm = Jvm

    object Jvm {
        const val nativeBundler = "org.scijava:native-lib-loader:${Version.jvm.nativeBundler}"
        const val slf4jApi = "org.slf4j:slf4j-api:${Version.jvm.slf4j}"
        const val slf4jNop = "org.slf4j:slf4j-nop:${Version.jvm.slf4j}"
    }
}
