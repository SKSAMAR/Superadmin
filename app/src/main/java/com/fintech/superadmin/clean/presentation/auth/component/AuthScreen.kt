package com.fintech.superadmin.clean.presentation.auth.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.presentation.auth.AuthViewModel
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel
) {
    BasicScreenState(state = authViewModel.state, animationModel = authViewModel.animationState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.sdp)
            ) {
                Row {
                    Spacer(modifier = Modifier.width(6.sdp))
                    Image(
                        modifier = Modifier
                            .size(35.sdp),
                        painter = painterResource(id = R.drawable.roundedicon),
                        contentDescription = "round_logo",
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(vertical = 10.sdp),
                    text = buildAnnotatedString {
                        append("Enter your mobile number")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Gray,
                                fontSize = 10.textSdp,
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append("\nTo use UPI enter the mobile number linked to your bank account")
                        }
                    },
                    fontSize = 18.textSdp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = authViewModel.mobileNumber.value,
                    onValueChange = {
                        if (it.isDigitsOnly() && it.length <= 10) {
                            authViewModel.mobileNumber.value = it.trim()
                        }
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "10 Digit Mobile Number",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 16.textSdp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    leadingIcon = {
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = "+ 91 - ",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 16.textSdp,
                                textAlign = TextAlign.End,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    maxLines = 1,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.textSdp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.SemiBold
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = {

                    })
                )

            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 60.sdp),
            text = buildAnnotatedString {
                append("By proceeding you are agreeing to YesPay Business's ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = 8.textSdp
                    )
                ) {
                    append("Terms & Conditions")
                }
            },
            fontSize = 8.textSdp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}