package com.fintech.superadmin.flight.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import com.fintech.superadmin.flight.util.common.sdp

@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    placeholderText: String = "Placeholder",
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    passwordVisible: Boolean = true
) {

    OutlinedTextField(
        modifier = modifier.fillMaxWidth().padding(top = 1.sdp),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colors.onSurface,
            fontSize = fontSize,
            fontWeight = fontWeight,
        ),

        placeholder = {
            Text(
                text = placeholderText,
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
    )
}