package com.fintech.superadmin.flight.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.fintech.superadmin.flight.util.common.sdp
import com.fintech.superadmin.flight.util.common.textSdp
import com.fintech.superadmin.ui.theme.BackgroundCardGrey

@Composable
fun BasicSearchView(
    value: String,
    hint: String,
    trailingIcon: (@Composable () -> Unit)? = null,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
    maxLength: Int = 35,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isEditable: Boolean = true,
    onValueChange: (String) -> Unit
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(3.sdp),
        backgroundColor = BackgroundCardGrey(),
    ) {
        CustomTextFieldMod(
            textAlign = textAlign,
            maxLength = maxLength,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            text = value,
            trailingIcon = trailingIcon,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "icon",
                    modifier = Modifier
                        .size(26.sdp)
                        .padding(end = 10.sdp),
                    tint = MaterialTheme.colors.onSurface.copy(0.4f)
                )
            },
            placeholderText = hint,
            modifier = modifier
                .background(
                    color = Color.Transparent,
                ),
            fontSize = 10.textSdp,
            isEditable = isEditable,
            onValueChange = {
                onValueChange(it)
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextFieldMod(
    maxLength: Int = 100,
    text: String,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    textAlign: TextAlign = TextAlign.Start,
    isEditable: Boolean = true,
    onValueChange: (String) -> Unit
) {

    val (focusRequester) = FocusRequester.createRefs()
    BasicTextField(
        textStyle = TextStyle(textAlign = textAlign, color = MaterialTheme.colors.onSurface),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        value = text,
        onValueChange = {
            if (isEditable) {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            }
        },
        maxLines = 1,
        modifier = Modifier
            .padding(vertical = 4.sdp, horizontal = 16.sdp)
            .height(28.sdp)
            .focusRequester(focusRequester),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colors.onSurface,
                            fontSize = fontSize
                        )
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        },
        enabled = isEditable
    )
}