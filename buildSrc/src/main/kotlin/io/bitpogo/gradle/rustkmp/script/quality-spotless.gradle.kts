/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.gradle.rustkmp.script

import gradle.kotlin.dsl.accessors._01a7fddcf81aa279b379d6fe3cb64505.spotless
import tech.antibytes.gradle.dependency.Version

plugins {
    id("com.diffplug.spotless")
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude(
            "buildSrc/build/",
            "**/buildSrc/build/",
            "**/rsrc/",
        )
        ktlint(Version.gradle.ktLint).editorConfigOverride(
            mapOf(
                "ktlint_disabled_rules" to "no-wildcard-imports",
                "ij_kotlin_imports_layout" to "*",
                "ij_kotlin_allow_trailing_comma" to "true",
                "ij_kotlin_allow_trailing_comma_on_call_site" to "true",
            )
        )
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint(Version.gradle.ktLint)
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
    format("misc") {
        target("**/*.adoc", "**/*.md", "**/.gitignore", ".java-version")

        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}
