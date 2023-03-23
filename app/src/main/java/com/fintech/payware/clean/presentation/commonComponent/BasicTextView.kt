package com.fintech.payware.clean.presentation.commonComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintech.payware.clean.util.common.CustomTextField
import com.fintech.payware.clean.util.sdp
import com.fintech.payware.clean.util.textSdp
import com.fintech.payware.ui.theme.BackgroundCardGrey
import com.fintech.payware.ui.theme.BackgroundHomeColor
import com.fintech.payware.ui.theme.LightestGrey


@Composable
fun BasicTextViewAddPro(
    value: MutableState<String>,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
    placeHolder: String = "",
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
            placeholderText = placeHolder,
            modifier = Modifier
                .background(
                    BackgroundHomeColor,
                    RoundedCornerShape(percent = 6)
                )
                .padding(vertical = 4.dp, horizontal = 18.dp)
                .height(35.dp),
            fontSize = 14.sp,
            isEditable = isEditable
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


@Composable
fun BasicTextNum(
    value: String,
    onValueChange: (String) -> Unit,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
    maxLength: Int = 35,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
    trailingIcon: (@Composable () -> Unit)? = null,
    isEditable: Boolean = true
) {

    Box(modifier = modifier) {
        BasicOutlinedTextNum(
            textAlign = textAlign,
            maxLength = maxLength,
            keyboardOptions = keyboardOptions,
            value = value,
            onValueChange = { onValueChange(it) },
            trailingIcon = trailingIcon,
            hint = "",
            modifier = Modifier
                .background(
                    Color.Transparent,
                )
                .padding(vertical = 2.sdp, horizontal = 2.sdp)
                .height(25.dp),
            isEditable = isEditable
        )
    }
}


@Composable
fun BasicOutlinedTextNum(
    value: String,
    hint: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
    maxLength: Int = 35,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isEditable: Boolean = true,
    onValueChange: (String) -> Unit
) {

    Box(modifier = Modifier) {
        CustomTextFieldModNum(
            textAlign = textAlign,
            maxLength = maxLength,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            text = value,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            placeholderText = hint,
            modifier = modifier
                .background(
                    Color.Transparent
                )
                .border(width = 0.5.dp, color = Color.Black)
                .padding(vertical = 1.sdp, horizontal = 1.sdp)
                .height(25.dp),
            fontSize = 4.textSdp,
            isEditable = isEditable,
            onValueChange = {
                onValueChange(it)
            }
        )
    }
}


@Composable
fun BasicOutlinedTextView(
    value: String,
    hint: String,
    leadingIcon: (@Composable () -> Unit)? = null,
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
        modifier = Modifier,
        shape = RoundedCornerShape(6.sdp),
        border = BorderStroke(width = .9.dp, color = MaterialTheme.colors.onSurface),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        CustomTextFieldMod(
            textAlign = textAlign,
            maxLength = maxLength,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            text = value,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
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


@Composable
fun BasicOutlined(
    value: String,
    hint: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
    maxLength: Int = 35,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isEditable: Boolean = true,
    maxLines: Int = 1,
    onValueChange: (String) -> Unit
) {

    Box(modifier = Modifier) {
        CustomTextFieldMod2(
            textAlign = textAlign,
            maxLength = maxLength,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            text = value,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            placeholderText = hint,
            modifier = modifier
                .background(
                    Color.Transparent
                )
                .border(width = 0.5.dp, color = Color.Gray)
                .padding(vertical = 4.sdp, horizontal = 16.sdp)
                .height(32.sdp),
            fontSize = 14.textSdp,
            isEditable = isEditable,
            onValueChange = {
                onValueChange(it)
            },
            maxLines = maxLines
        )
    }
}


@Composable
fun BasicTextBox(
    value: String,
    hint: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
    maxLength: Int = 35,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isEditable: Boolean = true,
    maxLines: Int = 1,
    onValueChange: (String) -> Unit
) {

    Box(modifier = Modifier) {
        CustomComplain(
            textAlign = textAlign,
            maxLength = maxLength,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            text = value,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            placeholderText = hint,
            modifier = modifier
                .background(
                    color = BackgroundHomeColor
                )
                .padding(vertical = 16.sdp, horizontal = 16.sdp),
            fontSize = 14.textSdp,
            isEditable = isEditable,
            onValueChange = {
                onValueChange(it)
            },
            maxLines = maxLines
        )
    }
}


@Composable
fun BasicOutlinedSearchView(
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
        modifier = Modifier,
        shape = RoundedCornerShape(6.sdp),
        border = BorderStroke(width = .9.dp, color = MaterialTheme.colors.onSurface),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
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


@Composable
fun OutlinedSearchView(
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
        modifier = Modifier,
        shape = RoundedCornerShape(18.sdp),
        border = BorderStroke(width = .7.dp, color = MaterialTheme.colors.onSurface),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
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

@Composable
fun BasicOutlinedContainerView(
    value: String,
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
        modifier = Modifier,
        shape = RoundedCornerShape(6.sdp),
        border = BorderStroke(width = .9.dp, color = MaterialTheme.colors.onSurface),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        CustomTextFieldMod(
            textAlign = textAlign,
            maxLength = maxLength,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            text = value,
            trailingIcon = trailingIcon,
            placeholderText = "",
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



@Composable
fun BasicGrocerySearchView(
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



@Composable
fun BasicOutlinedPasswordView(
    value: String,
    hint: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIconEnabled: Boolean = true,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
    maxLength: Int = 35,
    keyboardActions: KeyboardActions = KeyboardActions(),
    isEditable: Boolean = true,
    onValueChange: (String) -> Unit
) {


    var passwordVisible by remember { mutableStateOf(false) }
    val image = if (passwordVisible)
        Icons.Filled.Visibility
    else Icons.Filled.VisibilityOff

    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(6.sdp),
        border = BorderStroke(width = .9.dp, color = MaterialTheme.colors.onSurface),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {


        CustomPasswordFieldMod(
            textAlign = textAlign,
            maxLength = maxLength,
            keyboardActions = keyboardActions,
            text = value,
            leadingIcon = leadingIcon,
            placeholderText = hint,
            modifier = modifier
                .background(
                    color = Color.Transparent,
                ),
            fontSize = 10.textSdp,
            isEditable = isEditable,
            onValueChange = {
                onValueChange(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if (trailingIconEnabled) {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            modifier = Modifier
                                .size(14.dp)
                                .padding(end = 0.dp),
                            imageVector = image,
                            contentDescription = "select",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            },
            passwordVisible = passwordVisible
        )
    }
}


@Composable
fun DelOutlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    maxLength: Int = 100,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        maxLines = 1,
        singleLine = true,
        placeholder = {
            Text(text = hint)
        },
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun DelOutlinePasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    maxLength: Int = 500,
    modifier: Modifier = Modifier
) {

    var passwordVisible by remember { mutableStateOf(false) }
    val image = if (passwordVisible)
        Icons.Filled.Visibility
    else Icons.Filled.VisibilityOff

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        maxLines = 1,
        singleLine = true,
        placeholder = {
            Text(text = hint)
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisible = !passwordVisible
            }) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 0.dp),
                    imageVector = image,
                    contentDescription = "select",
                    contentScale = ContentScale.Fit,
                )
            }
        }
    )
}