package com.fintech.petoindia.auth.component

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.fintech.petoindia.R
import com.fintech.petoindia.auth.AuthViewModel
import com.fintech.petoindia.clean.presentation.commonComponent.BaseButton
import com.fintech.petoindia.clean.presentation.commonComponent.BasicOutlinedPasswordView
import com.fintech.petoindia.clean.presentation.commonComponent.BasicOutlinedTextView
import com.fintech.petoindia.clean.presentation.commonComponent.MyPinInput
import com.fintech.petoindia.clean.util.sdp
import com.fintech.petoindia.clean.util.textSdp
import com.fintech.petoindia.ui.theme.BackgroundCardGrey
import com.fintech.petoindia.util.Accessable
import kotlinx.coroutines.delay

@Composable
fun LoginContent(
    viewModel: AuthViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    val navController = rememberNavController()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        NavHost(navController = navController, startDestination = AuthNav.LoginPage.route) {
            composable(route = AuthNav.LoginPage.route) {
                LoginPage(navController = navController, viewModel = viewModel)
            }
            composable(route = AuthNav.RegisterPage.route) {
                RegisterPage(navController = navController, viewModel = viewModel)
            }
            composable(route = AuthNav.OTP.route) {
                OTPPage(navController = navController, viewModel = viewModel)
            }
            composable(route = AuthNav.CreatePassword.route) {
                CreatePasswordPage(navController = navController, viewModel = viewModel)
            }
            composable(route = AuthNav.ForgotPassword.route) {
                ForgotPasswordPage(navController = navController, viewModel = viewModel)
            }
        }
    }

}

@Composable
fun LoginPage(
    navController: NavController,
    viewModel: AuthViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            Spacer(modifier = Modifier.height(15.sdp))
            AsyncImage(
                model = R.drawable.logo,
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    //.padding(vertical = 30.sdp)
                    .align(Alignment.CenterHorizontally)
                    .size(130.sdp)
            )
            Spacer(modifier = Modifier.height(15.sdp))
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.sdp, vertical = 10.sdp),
            backgroundColor = BackgroundCardGrey(),
            shape = RoundedCornerShape(8.sdp),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.sdp, bottom = 12.sdp, start = 14.sdp, end = 14.sdp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Login",
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.textSdp
                )
                Spacer(modifier = Modifier.height(29.sdp))
                BasicOutlinedTextView(
                    hint = "Your email or phone",
                    value = viewModel.mobile.value,
                    onValueChange = { viewModel.mobile.value = it },
                    maxLength = 100,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.None
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.sdp))

                BasicOutlinedPasswordView(
                    hint = "Your Password",
                    value = viewModel.password.value,
                    onValueChange = { viewModel.password.value = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.sdp))
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { navController.navigate(AuthNav.ForgotPassword.route) },
                    text = "Forgot Password",
                    fontSize = 10.textSdp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(32.sdp))
                BaseButton(
                    text = "Login",
                    fontSize = 14.textSdp,
                    onClick = {
                        if (Accessable.isAccessable()) {
                            viewModel.loginMerchant(context)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(20.sdp))
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { navController.navigate(AuthNav.RegisterPage.route) },
                    text = buildAnnotatedString {
                        append("Don't have an account? ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Register")
                        }
                    },
                    fontSize = 10.textSdp,
                )
                Spacer(modifier = Modifier.height(10.sdp))

            }
        }
    }
}


@Composable
fun ForgotPasswordPage(
    navController: NavController,
    viewModel: AuthViewModel = viewModel(),
    context: Context = LocalContext.current
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.sdp, vertical = 10.sdp),
            shape = RoundedCornerShape(8.sdp),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.sdp, bottom = 12.sdp, start = 14.sdp, end = 14.sdp)
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(15.sdp))
                    AsyncImage(
                        model = R.drawable.logo,
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            //.padding(vertical = 30.sdp)
                            .align(Alignment.CenterHorizontally)
                            .size(130.sdp)
                    )
                    Spacer(modifier = Modifier.height(15.sdp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Forgot Password",
                        fontSize = 16.textSdp
                    )
                }
                Spacer(modifier = Modifier.height(10.sdp))
                BasicOutlinedTextView(
                    hint = "Enter your email or mobile number",
                    value = viewModel.mobile.value,
                    onValueChange = { viewModel.mobile.value = it },
                    maxLength = 100,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.None
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.sdp))
                BaseButton(
                    text = "Send OTP",
                    fontSize = 14.textSdp,
                    onClick = {
                        if (Accessable.isAccessable()) {
                            viewModel.sendForgotOTP(navController = navController)
                        }
                    }
                )


            }
        }
    }
}

@Composable
fun OTPPage(
    navController: NavController,
    viewModel: AuthViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    LaunchedEffect(key1 = viewModel.otpHash.value) {
        if (viewModel.otpHash.value.trim().isNotEmpty()) {
            viewModel.resendAble.value = false
            viewModel.timerCount.value = System.currentTimeMillis() + 300000
            delay(300000)
            viewModel.resendAble.value = true
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(15.sdp))
                AsyncImage(
                    model = R.drawable.logo,
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        //.padding(vertical = 30.sdp)
                        .align(Alignment.CenterHorizontally)
                        .size(130.sdp)
                )
            }

            Spacer(modifier = Modifier.height(15.sdp))
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "OTP Verification",
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.textSdp
                )
                Spacer(modifier = Modifier.height(2.sdp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Please enter your 6 Digit One Time \n" +
                            "Password ( OTP ). This OTP is valid for\n" +
                            "5 minutes",
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 12.textSdp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(25.sdp))

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.sdp), contentAlignment = Alignment.Center
            ) {
                MyPinInput(
                    value = viewModel.otp.value,
                    obscureText = "*",
                    length = 6,
                    disableKeypad = false,
                    onValueChanged = {
                        viewModel.otp.value = it
                    }
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.sdp, horizontal = 20.sdp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        if (Accessable.isAccessable()) {
                            viewModel.sendRegisterOTP(
                                navController = navController,
                                isFirst = false
                            )
                        }
                    },
                text = buildAnnotatedString {
                    append("Didn't received OTP? ")
                    withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append("Resend")
                    }
                },
                fontSize = 12.textSdp,
            )

            Spacer(modifier = Modifier.height(30.sdp))

            BaseButton(
                text = "Verify",
                fontSize = 14.textSdp,
                onClick = {
                    if (Accessable.isAccessable()) {
                        val task =
                            navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                                key = "task"
                            )
                        navController.navigate(AuthNav.CreatePassword.route)
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "task",
                            value = task
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun RegisterPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(),
    context: Context = LocalContext.current
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column {
            Spacer(modifier = Modifier.height(15.sdp))
            AsyncImage(
                model = R.drawable.logo,
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    //.padding(vertical = 30.sdp)
                    .align(Alignment.CenterHorizontally)
                    .size(130.sdp)
            )
            Spacer(modifier = Modifier.height(15.sdp))
        }

        Column {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Register",
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.textSdp
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.sdp, vertical = 8.sdp),
                backgroundColor = BackgroundCardGrey(),
                shape = RoundedCornerShape(8.sdp),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.sdp, bottom = 8.sdp, start = 14.sdp, end = 14.sdp)
                ) {

                    Spacer(modifier = Modifier.height(20.sdp))
                    BasicOutlinedTextView(
                        hint = "Your Full Name",
                        value = viewModel.firstName.value,
                        onValueChange = { viewModel.firstName.value = it.uppercase() },
                        maxLength = 100,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Characters
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.sdp))


                    BasicOutlinedTextView(
                        hint = "Enter your email address",
                        value = viewModel.email.value,
                        onValueChange = { viewModel.email.value = it.lowercase() },
                        maxLength = 100,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.None
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.sdp))

                    BasicOutlinedTextView(
                        hint = "Enter your mobile number",
                        value = viewModel.mobile.value,
                        onValueChange = { viewModel.mobile.value = it },
                        maxLength = 10,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number,
                            capitalization = KeyboardCapitalization.None
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.sdp))

                    BaseButton(
                        text = "Signup",
                        fontSize = 14.textSdp,
                        onClick = {
                            if (Accessable.isAccessable()) {
                                viewModel.sendRegisterOTP(navController)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(20.sdp))
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable { navController.popBackStack() },
                        text = buildAnnotatedString {
                            append("Already have an account? ")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Log in")
                            }
                        },
                        fontSize = 10.textSdp,
                    )
                    Spacer(modifier = Modifier.height(7.sdp))

                }

            }
        }

    }
}


@Composable
fun CreatePasswordPage(
    navController: NavController,
    viewModel: AuthViewModel = viewModel(),
    context: Context = LocalContext.current
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Column {
            Spacer(modifier = Modifier.height(15.sdp))
            AsyncImage(
                model = R.drawable.logo,
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    //.padding(vertical = 30.sdp)
                    .align(Alignment.CenterHorizontally)
                    .heightIn(min = 100.sdp, max = 130.sdp)
            )
            Spacer(modifier = Modifier.height(15.sdp))
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.sdp, vertical = 10.sdp),
            backgroundColor = BackgroundCardGrey(),
            shape = RoundedCornerShape(8.sdp),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.sdp, bottom = 15.sdp, start = 14.sdp, end = 14.sdp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Create Password",
                        fontSize = 16.textSdp
                    )
                }
                Spacer(modifier = Modifier.height(25.sdp))
                BasicOutlinedPasswordView(
                    hint = "Password",
                    value = viewModel.password.value,
                    onValueChange = { viewModel.password.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIconEnabled = false
                )
                Spacer(modifier = Modifier.height(10.sdp))

                BasicOutlinedPasswordView(
                    hint = "Confirm Password",
                    value = viewModel.cPassword.value,
                    onValueChange = { viewModel.cPassword.value = it },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(25.sdp))

                BaseButton(
                    text = "Save",
                    fontSize = 14.textSdp,
                    onClick = {
                        if (Accessable.isAccessable()) {
                            val task =
                                navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                                    key = "task"
                                )
                            when (task ?: "") {
                                "create" -> {
                                    viewModel.doOnBoard(navController)
                                }

                                "forgot" -> {
                                    viewModel.changePassword(navController)
                                }
                            }
                        }
                    }
                )


            }
        }
    }
}