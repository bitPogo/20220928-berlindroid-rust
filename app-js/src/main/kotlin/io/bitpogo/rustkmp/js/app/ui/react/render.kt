/* ktlint-disable filename */
/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.rustkmp.js.app.ui.react

import react.ChildrenBuilder
import react.Fragment
import react.create

fun render(content: ChildrenBuilder.() -> Unit) = Fragment.create(content)
