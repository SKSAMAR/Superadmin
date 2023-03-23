package com.fintech.payware.auth.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fintech.payware.auth.AuthViewModel
import com.fintech.payware.clean.presentation.common.BasicScreenState

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel()
) {
    viewModel.state.value
    BasicScreenState(
        modifier = Modifier.fillMaxSize(),
        state = viewModel.state
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LoginContent(viewModel = viewModel)
        }

    }

}

sealed class AuthNav(val name: String, val route: String){
    object LoginPage: AuthNav(name = "Login", route = "login")
    object RegisterPage: AuthNav(name = "Register", route = "register")
    object OTP: AuthNav(name = "OTP", route = "otp")
    object ForgotPassword: AuthNav(name = "Forgot Password", route = "forgot_password")
    object CreatePassword: AuthNav(name = "Create Password", route = "create_password")
}



sealed class ChangePasswordNav(val name: String, val route: String){
    object InitiatePage: ChangePasswordNav(name = "Login", route = "login")
    object OTP: ChangePasswordNav(name = "OTP", route = "otp")
    object CreatePassword: ChangePasswordNav(name = "Create Password", route = "create_password")
}
