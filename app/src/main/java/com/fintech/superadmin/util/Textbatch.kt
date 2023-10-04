package com.fintech.superadmin.util

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintech.superadmin.ui.theme.IconColor
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Textbatch(
    hint: String,
    value: String,
    textcolor: Color = Color.Gray,
    editable: Boolean = true,
    highlt: Boolean = false,
    singleLine: Boolean = false,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onChange(it)
        },
        singleLine = singleLine,
        label = {
            Text(text = hint, color = textcolor)
        },
        placeholder = {
            Text(text = hint, color = textcolor)
        },
        enabled = editable,
        modifier = modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(.9f),
        colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = Color.Black),
    )

}


@Composable
fun Chooser(txtval: String, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(.9f)
            .height(60.dp)
            .padding(vertical = 5.dp),
        backgroundColor = Color.White,
        border = BorderStroke(width = 1.dp, Color.Gray)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = txtval,
                    color = IconColor,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 12.dp),
                    tint = Color.Gray
                )
            }
        }
    }

}

@Composable
fun Textbatchnum(
    hint: String,
    value: String,
    editable: Boolean = true,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onChange(it)
        },
        label = {
            Text(text = hint)
        },
        placeholder = {
            Text(text = hint)
        },
        enabled = editable,
        modifier = modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(.9f),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = IconColor, focusedLabelColor = IconColor
        )
    )

}

const val PIN_VIEW_TYPE_UNDERLINE = 0
const val PIN_VIEW_TYPE_BORDER = 1

@Composable
fun PinView(
    pinText: String,
    onPinTextChange: (String) -> Unit,
    digitColor: Color = MaterialTheme.colors.onBackground,
    digitSize: TextUnit = 16.sp,
    containerSize: Dp = digitSize.value.dp * 2,
    digitCount: Int = 4,
    type: Int = PIN_VIEW_TYPE_UNDERLINE,
) {
    BasicTextField(
        modifier = Modifier.fillMaxWidth(),
        value = pinText,
        onValueChange = onPinTextChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = PasswordVisualTransformation(),
        decorationBox = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(digitCount) { index ->
                    DigitView(index, pinText, digitColor, digitSize, containerSize, type = type)
//                    if (digitCount == 4) Spacer(modifier = Modifier.width(40.dp))
//                    else Spacer(modifier = Modifier.width(10.dp))
                }
            }
        })
}


@Composable
private fun DigitView(
    index: Int,
    pinText: String,
    digitColor: Color,
    digitSize: TextUnit,
    containerSize: Dp,
    type: Int = PIN_VIEW_TYPE_UNDERLINE,
) {
    val modifier = if (type == PIN_VIEW_TYPE_BORDER) {
        Modifier
            .width(containerSize)
            .border(
                width = 1.dp, color = digitColor, shape = MaterialTheme.shapes.medium
            )
            .padding(bottom = 3.dp)
    } else Modifier.width(containerSize)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(start = 3.dp)
    ) {
        Text(
            text = if (index >= pinText.length) "" else pinText[index].toString(),
            color = digitColor,
            modifier = modifier,
            style = MaterialTheme.typography.body1,
            fontSize = digitSize,
            textAlign = TextAlign.Center
        )
        if (type == PIN_VIEW_TYPE_UNDERLINE) {
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .width(5.dp)
            )
            Box(
                modifier = Modifier
                    .background(digitColor)
                    .height(1.dp)
                    .padding(horizontal = 10.dp)
                    .width(containerSize)
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String = "Search Accounts",
    value: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        var textState by remember { mutableStateOf("") }
        val maxLength = 110
        val lightBlue = Color(0xffd8e6ff)
        val blue = Color(0xff76a9ff)
        TextField(
            modifier = Modifier.fillMaxWidth(.9f),
            value = value,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = lightBlue,
                cursorColor = Color.Black,
                disabledLabelColor = lightBlue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(text = hint, color = Color.Gray)
            },
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            trailingIcon = trailingIcon,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search, contentDescription = null
                )
            })
    }
}


@SuppressLint("SimpleDateFormat")
fun toSpecificDepartDatesString(date: Date): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(date)

}

//@Composable
//fun FailedLoaderval) {
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.failed))
//    val progress by animateLottieCompositionAsState(composition)
//    LottieAnimation(
//        composition = composition,
//        progress = { progress },
//    )
//}