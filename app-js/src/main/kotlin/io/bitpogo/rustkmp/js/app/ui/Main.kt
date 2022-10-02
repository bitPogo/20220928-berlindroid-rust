/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.rustkmp.js.app.ui

import browser.document
import io.bitpogo.rustkmp.js.app.ui.atom.Divider
import io.bitpogo.rustkmp.js.app.ui.atom.SimpleButton
import io.bitpogo.rustkmp.js.app.ui.atom.SimpleText
import io.bitpogo.rustkmp.js.app.ui.atom.SingleLineEditableText
import io.bitpogo.rustkmp.js.app.ui.react.render
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import react.Component
import react.Props
import react.ReactNode
import react.State
import react.dom.client.createRoot
import react.dom.html.ReactHTML.div

external interface AppState : State
external interface AppProps : Props

class Application(
    private val viewModel: RustOnRailsVM,
) : Component<AppProps, AppState>() {
    private fun update(
        result: SimpleText,
    ) {
        MainScope().launch {
            async { viewModel.calculate() }.await()
            result.props.text = viewModel.result.value
        }
    }

    override fun render(): ReactNode {
        val app = this
        val result = SimpleText()
        result.props.text = viewModel.result.value

        val number1 = SingleLineEditableText("Number1") { event ->
            viewModel.setNumber1(event.target.value as String)
        }
        number1.props.value = viewModel.number1.value

        val number2 = SingleLineEditableText("Number2") { event ->
            viewModel.setNumber2(event.target.value as String)
        }
        number2.props.value = viewModel.number2.value

        val operator = SingleLineEditableText("Operator") { event ->
            viewModel.setOperator(event.target.value[0] as String)
        }
        operator.props.value = viewModel.operator.value.toString()

        val button = SimpleButton("Calculate!") {
            app.update(result)
        }
        val divider = Divider("inbetween")

        return render {
            div {
                +number1.render()
                +divider.render()
                +number2.render()
                +divider.render()
                +operator.render()
                +divider.render()
                +button.render()
                +divider.render()
                +result.render()
            }
        }
    }
}

fun main() {
    val viewModel = RustOnRailsVM()
    document.getElementById("app")?.apply {
        val root = createRoot(this)
        val app = Application(viewModel)
        root.render(app.render())
    }
}
