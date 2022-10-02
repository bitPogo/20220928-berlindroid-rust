/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.gradle.rustkmp.dependency

object Version {

    const val kotlin = "1.7.10"

    val gradle = Gradle
    val antibytes = Antibytes

    object Gradle {
        /**
         * [AnitBytes GradlePlugins](https://github.com/bitPogo/gradle-plugins)
         */
        const val antibytes = "9e2ffe9"

        /**
         * [Spotless](https://plugins.gradle.org/plugin/com.diffplug.gradle.spotless)
         */
        const val spotless = "6.11.0"

        /**
         * [Mozilla Rust Gradle](https://github.com/mozilla/rust-android-gradle/releases)
         */
        const val rust = "0.9.3"
    }

    object Antibytes {
        const val test = "9401af5"
        const val kfixture = "0.4.0"
        const val kmock = "0.3.0-rc04"
    }

    val jvm = Jvm

    object Jvm {
        /**
         * [native library loader](https://search.maven.org/artifact/org.scijava/native-lib-loader/2.4.0/jar)
         */
        const val nativeBundler = "2.4.0"
        /**
         * [Log4J](http://www.slf4j.org/)
         */
        const val slf4j = "1.7.36"
    }
}
