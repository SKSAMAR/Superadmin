package com.fintech.superadmin.activities.fingBoard

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.util.StartGettingLocation
import com.fintech.superadmin.viewmodel.FingPayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FingBoardHome : BaseFingerFingpayActivity() {

    private val viewModel by viewModels<FingPayViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StartGettingLocation.setAllTheLocations(this@FingBoardHome)
        setContent {
            SuperAdminTheme(darkTheme = false) {
                Surface(color = MaterialTheme.colors.surface) {
                    MainContent()
                }
            }
        }
    }

    @Composable
    private fun MainContent() {
        viewModel.navController = rememberNavController()
        BasicScreenState(state = viewModel.state, animationModel = viewModel.animationState) {
            NavHost(
                navController = viewModel.navController,
                startDestination = FingNav.PanSelection.route
            ) {
                composable(FingNav.PanSelection.route) {
                    PanSelectionScreen()
                }
                composable(FingNav.AadhaarSelection.route) {
                    AadhaarSelectionScreen()
                }
                composable(FingNav.OtpSelection.route) {
                    PanOTPScreen()
                }
                composable(FingNav.CompleteKyc.route) {
                    CompleteKycScreen()
                }
            }
        }
    }

    @Composable
    private fun PanSelectionScreen(
        context: Context = LocalContext.current
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "AePS Registration")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.verifyPanNumber(context)
                        }
                ) {
                    Text(
                        text = "Verify",
                        modifier = Modifier
                            .padding(vertical = 10.sdp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .padding(10.sdp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.panNumber,
                    onValueChange = {
                        viewModel.panNumber = it
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Enter Pan Number",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 16.textSdp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.PermIdentity,
                            contentDescription = "Pan ID"
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
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Characters
                    )
                )
            }
        }
    }

    @Composable
    private fun AadhaarSelectionScreen(
        context: Context = LocalContext.current
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "AePS Registration")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.sendOTP(context)
                        }
                ) {
                    Text(
                        text = "Verify",
                        modifier = Modifier
                            .padding(vertical = 10.sdp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .padding(10.sdp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.aadhaarNumber,
                    onValueChange = {
                        viewModel.aadhaarNumber = it
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Enter Aadhaar Number",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 16.textSdp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.PermIdentity,
                            contentDescription = "Aadhaar ID"
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
                        keyboardType = KeyboardType.Number,
                        capitalization = KeyboardCapitalization.Characters
                    ),
                )
            }
        }
    }

    @Composable
    private fun PanOTPScreen(
        context: Context = LocalContext.current
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "AePS Registration")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.validateOTP(context)
                        }
                ) {
                    Text(
                        text = "Verify",
                        modifier = Modifier
                            .padding(vertical = 10.sdp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .padding(10.sdp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.otp,
                    onValueChange = {
                        viewModel.otp = it
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Enter OTP",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 16.textSdp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Password,
                            contentDescription = "OTP"
                        )
                    },
                    maxLines = 1,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.textSdp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Characters
                    )
                )
                Text(
                    text = "Resend OTP",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.textSdp
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(vertical = 10.sdp, horizontal = 10.sdp)
                        .clickable { viewModel.reSendOTP(context) }
                )
            }
        }
    }

    @Composable
    private fun CompleteKycScreen(
        context: Context = LocalContext.current
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "AePS Registration")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .padding(10.sdp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        captureFingerBegin()
                    }
                ) {
                    Text(
                        text = "Capture Fingerprint",
                        fontSize = 18.textSdp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    override fun fingerPrintCaptured(result: String) {
        result.let {
            viewModel.finalizeCompleteKyc(this@FingBoardHome, it)
        }
    }
}