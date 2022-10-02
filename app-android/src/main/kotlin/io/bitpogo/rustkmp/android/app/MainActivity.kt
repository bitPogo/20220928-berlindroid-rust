/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.rustkmp.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.bitpogo.rustkmp.android.app.ui.atom.SimpleButton
import io.bitpogo.rustkmp.android.app.ui.atom.SingleLineEditableText
import io.bitpogo.rustkmp.android.app.ui.theme.RustKmpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = RustOnRailsVM()
        setContent {
            val number1 = viewModel.number1.collectAsState()
            val number2 = viewModel.number2.collectAsState()
            val operator = viewModel.operator.collectAsState()
            val result = viewModel.result.collectAsState()

            RustKmpTheme {
                Column {
                    SingleLineEditableText(
                        label = "Number1",
                        value = number1.value,
                        onChange = { new ->
                            viewModel.setNumber1(new)
                        },
                    )
                    Divider(modifier = Modifier.height(5.dp))
                    SingleLineEditableText(
                        label = "Number2",
                        value = number2.value,
                        onChange = { new ->
                            viewModel.setNumber2(new)
                        },
                    )
                    Divider(modifier = Modifier.height(5.dp))
                    SingleLineEditableText(
                        label = "Operator",
                        value = operator.value.toString(),
                        onChange = { new ->
                            viewModel.setOperator(new)
                        },
                    )
                    Divider(modifier = Modifier.height(5.dp))
                    SimpleButton(
                        label = "Calculate!",
                        onClick = viewModel::calculate,
                    )
                    Divider(modifier = Modifier.height(5.dp))
                    Text("Result: ${result.value}")
                }
            }
        }
    }
}
