package com.fintech.prepe.clean.presentation.auth.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.fintech.prepe.R
import com.fintech.prepe.clean.presentation.auth.AuthViewModel
import com.fintech.prepe.clean.presentation.common.BasicScreenState
import com.fintech.prepe.clean.util.sdp
import com.fintech.prepe.clean.util.textSdp

@Composable
fun AuthPassword(
    authViewModel: AuthViewModel
) {
    var passwordVisible by remember{
        mutableStateOf(false)
    }
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
                    .padding(top = 5.sdp)
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
                        append("Enter your Password of ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.primary,
                            )
                        ) {
                            append(authViewModel.mobileNumber.value)
                        }
                    },
                    fontSize = 18.textSdp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = authViewModel.password.value,
                    onValueChange = {
                        authViewModel.password.value = it
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Enter Password",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 16.textSdp,
                                textAlign = TextAlign.Start,
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
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        // Please provide localized description for accessibility services
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = {passwordVisible = !passwordVisible}){
                            Icon(
                                imageVector  = image,
                                description,
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

                )
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Forgot Password?",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 10.textSdp,
                        fontWeight = FontWeight.Normal
                    )
                )

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(.85f)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 60.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(12.sdp),
                imageVector = Icons.Default.Flight,
                contentDescription = "flight",
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = "LOGIN BY SENDING SMS",
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.textSdp
            )
        }
    }
}