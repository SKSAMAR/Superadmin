package com.fintech.paytoindia.clean.util.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintech.paytoindia.ui.theme.IconColor
import com.fintech.paytoindia.ui.theme.LightestGrey

@Composable
fun BasicSelectView(
    value: MutableState<String>,
    textAlign: TextAlign = TextAlign.Start,
    placeholderText: String = "",
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = {
        Image(
            modifier = Modifier
                .size(24.dp)
                .padding(end = 10.dp),
            painter = painterResource(id =  com.fintech.paytoindia.R.drawable.focus),
            contentDescription = "capture",
            contentScale = ContentScale.Fit
        )
    },
    onTextChange: (String) -> Unit = {}
) {
    Box(modifier = modifier) {
        CustomSelectorField(
            textAlign = textAlign,
            text = value,
            trailingIcon = trailingIcon,
            placeholderText = placeholderText,
            modifier = Modifier
                .background(
                    LightestGrey,
                    RoundedCornerShape(percent = 12)
                )
                .padding(vertical = 4.dp, horizontal = 18.dp)
                .height(30.dp),
            fontSize = 14.sp,
            onTextChange = onTextChange
        )
    }
}

@Composable
fun CustomSelectorField(
    text: MutableState<String>,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "",
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    textAlign: TextAlign = TextAlign.Start,
    onTextChange: (String) -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {

        BasicTextField(
            textStyle = TextStyle(
                textAlign = textAlign,
                color = IconColor
            ),
            value = text.value,
            onValueChange = {
                onTextChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colors.surface,
                    MaterialTheme.shapes.small,
                ),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) leadingIcon()
                    Box(Modifier.weight(1f)) {
                        if (text.value.isEmpty()) Text(
                            placeholderText,
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                fontSize = fontSize
                            )
                        )
                        innerTextField()
                    }
                    if (trailingIcon != null) trailingIcon()
                }
            },
            enabled = false,
            readOnly = true,
        )

    }
}

@Composable
fun BasicTextView(
    value: MutableState<String>,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
    maxLength: Int = 35,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isEditable: Boolean = true
) {

    Box(modifier = modifier) {
        CustomTextField(
            textAlign = textAlign,
            maxLength = maxLength,
            keyboardOptions = keyboardOptions,
            text = value,
            trailingIcon = null,
            placeholderText = "",
            modifier = Modifier
                .background(
                    LightestGrey,
                    RoundedCornerShape(percent = 12)
                )
                .padding(vertical = 4.dp, horizontal = 18.dp)
                .height(30.dp),
            fontSize = 14.sp,
            isEditable = isEditable
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextField(
    maxLength: Int = 20,
    text: MutableState<String>,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    textAlign: TextAlign = TextAlign.Start,
    isEditable: Boolean = true
) {

    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current
    BasicTextField(
        textStyle = TextStyle(textAlign = textAlign),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        value = text.value,
        onValueChange = {
            if (isEditable) {
                if (it.length <= maxLength) text.value = it
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .background(
                MaterialTheme.colors.surface,
                MaterialTheme.shapes.small,
            ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.value.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                            fontSize = fontSize
                        )
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}