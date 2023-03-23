package com.fintech.payware.clean.presentation.commonComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fintech.payware.clean.util.sdp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpCell(
    modifier: Modifier,
    value: Char?,
    isCursorVisible: Boolean = false,
    obscureText: String?
) {
    val scope = rememberCoroutineScope()
    val (cursorSymbol, setCursorSymbol) = remember { mutableStateOf("") }

    LaunchedEffect(key1 = cursorSymbol, isCursorVisible) {
        if (isCursorVisible) {
            scope.launch {
                delay(350)
                setCursorSymbol(if (cursorSymbol.isEmpty()) "|" else "")
            }
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = if (isCursorVisible) cursorSymbol else if (!obscureText.isNullOrBlank() && value?.toString()
                    .isNullOrBlank().not()
            ) obscureText else value?.toString() ?: "",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
/**
 * @param obscureText Set null, if want to show the number.
 */
@Composable
fun MyPinInput(
    modifier: Modifier = Modifier,
    length: Int = 5,
    value: String = "",
    disableKeypad: Boolean = false,
    obscureText: String? = "*",
    onValueChanged: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    TextField(
        readOnly = disableKeypad,
        value = value,
        onValueChange = {
            if (it.length <= length) {
                if (it.all { c -> c in '0'..'9' }) {
                    onValueChanged(it)
                }
                if (it.length >= length) {
                    keyboard?.hide()
                }
            }
        },
        // Hide the text field
        modifier = Modifier
            .size(0.dp)
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(length) {

            Card(
                modifier = modifier,
                border = BorderStroke(width = .9.dp, color = MaterialTheme.colors.onSurface),
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                shape = RectangleShape
            ) {
                OtpCell(
                    modifier = modifier
                        //.border(width = 0.5.dp, color = Color.Black)
                        //.padding(top = 4.sdp, bottom = 4.sdp, start = 16.sdp)
                        .height(32.sdp)
                        .width(28.sdp)
                        .clickable {
                            focusRequester.requestFocus()
                            keyboard?.show()
                        },
                    value = value.getOrNull(it),
                    isCursorVisible = value.length == it,
                    obscureText
                )
            }
        }
    }
}