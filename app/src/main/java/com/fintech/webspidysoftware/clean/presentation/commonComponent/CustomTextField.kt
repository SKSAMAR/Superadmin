package com.fintech.webspidysoftware.clean.presentation.commonComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.fintech.webspidysoftware.clean.util.sdp
import com.fintech.webspidysoftware.clean.util.textSdp
import com.yogeshpaliyal.speld.OtpCell

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextField(
    text: MutableState<String>,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize
) {
    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current
    BasicTextField(
        value = text.value,
        onValueChange = { text.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colors.surface,
                MaterialTheme.shapes.small,
            )
            .focusRequester(focusRequester),
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { keyboardController?.hide() }
        )
    )
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
        readOnly = !isEditable,
        textStyle = TextStyle(textAlign = textAlign),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        value = text.value,
        onValueChange = {
            if (isEditable) {
                if (keyboardOptions.keyboardType == KeyboardType.Number) {
                    if (it.length <= maxLength) text.value = it
                } else {
                    if (it.length <= maxLength) text.value = it
                }
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextFieldModNum(
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
        textStyle = TextStyle(textAlign = textAlign),
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
            .focusRequester(focusRequester)
            .background(
                Color.Transparent,
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
                    if (text.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = Color.Transparent,
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextFieldMod2(
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
    maxLines: Int = 1,
    onValueChange: (String) -> Unit
) {

    val (focusRequester) = FocusRequester.createRefs()
    BasicTextField(
        textStyle = TextStyle(textAlign = textAlign),
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
        maxLines = maxLines,
        modifier = Modifier
            .focusRequester(focusRequester)
            .background(
                MaterialTheme.colors.surface,
                MaterialTheme.shapes.small,
            ),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f), contentAlignment = Alignment.TopStart) {
                    if (text.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = Color.Gray,
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomComplain(
    maxLength: Int = 1000,
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
    maxLines: Int = 1,
    onValueChange: (String) -> Unit
) {

    val (focusRequester) = FocusRequester.createRefs()
    BasicTextField(
        textStyle = TextStyle(textAlign = textAlign),
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
        maxLines = maxLines,
        modifier = Modifier
            .focusRequester(focusRequester)
            .background(
                MaterialTheme.colors.surface,
                MaterialTheme.shapes.small,
            ),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.Top
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f), contentAlignment = Alignment.TopStart) {
                    if (text.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = Color.Gray,
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomPasswordFieldMod(
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
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean
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
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.sdp, bottom = 4.sdp, start = 16.sdp, end = 1.sdp)
            .height(32.sdp)
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
        }
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextFieldStateless(
    maxLength: Int = 20,
    text: String,
    onValueChange: (String) -> Unit,
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
        value = text,
        onValueChange = {
            if (isEditable) {
                if (it.length <= maxLength) onValueChange(it)
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
                    if (text.isEmpty()) Text(
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextNumber(
    text: MutableState<String>,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                if (it.length <= 20) {
                    text.value = it
                }
            }
        },
        modifier = modifier
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyOutlineTextField(
    value: MutableState<String>,
    placeholderText: String?,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    textAlign: TextAlign = TextAlign.Start,
) {
    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .background(
                MaterialTheme.colors.surface,
                MaterialTheme.shapes.small,
            ),
        value = value.value,
        onValueChange = {
            value.value = it
        },
        label = {
            placeholderText?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = placeholderText,
                    style = LocalTextStyle.current.copy(
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        fontSize = fontSize
                    )
                )
            }
        },
        maxLines = 1,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.textSdp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PinInputPersonal(
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
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(length) {
            OtpCell(
                modifier = modifier
                    .size(width = 36.sdp, height = 47.sdp)
                    .clip(MaterialTheme.shapes.large)
                    .background(
                        Color.Transparent,
                    )
                    .clickable {
                        focusRequester.requestFocus()
                        keyboard?.show()
                    },
                value = value.getOrNull(it),
                isCursorVisible = value.length == it,
                obscureText
            )
            Spacer(modifier = Modifier.size(8.sdp))
        }
    }
}

