/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.rustkmp.js.app.ui.atom

import csstype.ClassName
import io.bitpogo.rustkmp.js.app.ui.react.render
import react.Component
import react.Props
import react.ReactNode
import react.State
import react.dom.html.ReactHTML.div

class Divider(
    private val className: String,
) : Component<Props, State>() {
    override fun render(): ReactNode {
        return render {
            div {
                this.className = ClassName(this@Divider.className)
            }
        }
    }
}
