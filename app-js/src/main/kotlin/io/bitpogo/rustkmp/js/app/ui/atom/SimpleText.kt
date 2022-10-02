/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.rustkmp.js.app.ui.atom

import io.bitpogo.rustkmp.js.app.ui.react.render
import react.Component
import react.Key
import react.Props
import react.ReactNode
import react.State
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p

external interface SimpleTextProps : Props {
    var text: String
}

fun SimpleTextProps() = object : SimpleTextProps {
    override var text = ""
    override var key: Key?
        get() = TODO("Not yet implemented")
        set(value) {}
}

class SimpleText : Component<SimpleTextProps, State>(SimpleTextProps()) {
    override fun shouldComponentUpdate(nextProps: SimpleTextProps, nextState: State): Boolean = true

    override fun render(): ReactNode {
        return render {
            div {
                p {
                    +"Result: ${this@SimpleText.props.text}"
                }
            }
        }
    }
}
