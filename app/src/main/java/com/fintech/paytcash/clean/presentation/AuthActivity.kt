package com.fintech.paytcash.clean.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fintech.paytcash.clean.common.BaseComponentAct
import com.fintech.paytcash.clean.presentation.auth.component.AuthScreen
import com.fintech.paytcash.clean.presentation.auth.AuthViewModel
import com.fintech.paytcash.clean.presentation.auth.component.AuthPassword
import com.fintech.paytcash.clean.presentation.navigation.AuthNavigation
import com.fintech.paytcash.ui.theme.YespayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseComponentAct() {

    val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YespayTheme(darkTheme = false) {
                AuthHomeScreen()
            }
        }
    }

    @Composable
    private fun AuthHomeScreen() {
        viewModel.navHostController = rememberNavController()
        NavHost(navController = viewModel.navHostController, startDestination = AuthNavigation.CREDENTIAL.route) {
            composable(AuthNavigation.CREDENTIAL.route) {
                BaseScaffold(
                    bottomBar = {
                        if (!viewModel.state.value.isLoading) {
                            BottomAppBar(
                                modifier = Modifier.fillMaxWidth(),
                                backgroundColor = if (viewModel.mobileNumber.value.trim().length < 10) Color.Gray else MaterialTheme.colors.primary
                            ) {
                                Button(
                                    modifier = Modifier.fillMaxSize(),
                                    onClick = {
                                        viewModel.tryLogging()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (viewModel.mobileNumber.value.trim().length < 10) Color.Gray else MaterialTheme.colors.primary
                                    )
                                ) {
                                    Text(
                                        text = "PROCEED",
                                        color = if (viewModel.mobileNumber.value.trim().length < 10) Color.DarkGray else Color.White
                                    )
                                }
                            }
                        }
                    }
                ) {
                    AuthScreen(viewModel)
                }
            }

            composable(AuthNavigation.PASSWORD.route) {
                BaseScaffold(
                    topBar = {
                        TopAppBar(
                            elevation = 0.dp,
                            backgroundColor = Color.Transparent,
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        onBackPressed()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "back",
                                    )
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = {}
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.HelpOutline,
                                        contentDescription = "help",
                                    )
                                }
                            },
                            title = {}
                        )
                    },
                    bottomBar = {
                        if (!viewModel.state.value.isLoading) {
                            BottomAppBar(
                                modifier = Modifier.fillMaxWidth(),
                                backgroundColor = if (viewModel.password.value.trim().length < 4) Color.Gray else MaterialTheme.colors.primary
                            ) {
                                Button(
                                    modifier = Modifier.fillMaxSize(),
                                    onClick = {

                                        viewModel.tryLoggingWithPassword(this@AuthActivity)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (viewModel.password.value.trim().length < 4) Color.Gray else MaterialTheme.colors.primary
                                    )
                                ) {
                                    Text(
                                        text = "VERIFY",
                                        color = if (viewModel.password.value.trim().length < 4) Color.DarkGray else Color.White
                                    )
                                }
                            }
                        }
                    }
                ) {
                    AuthPassword(viewModel)
                }
            }
        }
    }

}