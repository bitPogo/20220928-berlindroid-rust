/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache v2.0
 */

package io.bitpogo.rustkmp.android.app.ui.atom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.bitpogo.rustkmp.android.app.ui.theme.Blue
import io.bitpogo.rustkmp.android.app.ui.theme.DeepBlack
import io.bitpogo.rustkmp.android.app.ui.theme.DeepRed
import io.bitpogo.rustkmp.android.app.ui.theme.LightDarkGray
import io.bitpogo.rustkmp.android.app.ui.theme.LightGray

@Composable
fun SingleLineEditableText(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    isError: Boolean = false,
    underlineIndicator: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val labelField = @Composable { Text(text = label) }
    val colours = TextFieldDefaults.outlinedTextFieldColors(
        textColor = DeepBlack,
        disabledTextColor = LightDarkGray,
        cursorColor = DeepBlack,
        placeholderColor = LightDarkGray,
        focusedBorderColor = Blue,
        unfocusedBorderColor = LightGray,
        disabledBorderColor = LightGray,
        errorBorderColor = DeepRed,
        focusedLabelColor = DeepBlack,
        unfocusedLabelColor = DeepBlack,
        disabledLabelColor = DeepBlack,
        errorLabelColor = DeepBlack,
    )

    if (!underlineIndicator) {
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            label = labelField,
            placeholder = labelField,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            colors = colours,
            isError = isError,
            keyboardOptions = keyboardOptions,
        )
    } else {
        TextField(
            value = value,
            onValueChange = onChange,
            label = labelField,
            placeholder = labelField,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            colors = colours,
            isError = isError,
            keyboardOptions = keyboardOptions,
        )
    }
}
