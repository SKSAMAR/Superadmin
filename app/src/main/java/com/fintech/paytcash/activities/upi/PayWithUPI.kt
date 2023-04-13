package com.fintech.paytcash.activities.upi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintech.paytcash.data.qrPayement.UPICredential
import com.fintech.paytcash.ui.theme.LightestRed
import com.fintech.paytcash.ui.theme.YespayTheme
import com.fintech.paytcash.util.Constant
import com.fintech.paytcash.util.ExecuteUtil
import com.fintech.paytcash.viewmodel.UPIViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayWithUPI: ComponentActivity() {

    val viewModel by viewModels<UPIViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.upiCredential = intent.getSerializableExtra(Constant.UPI_CREDENTIALS) as UPICredential
        viewModel.amount.value =  viewModel.upiCredential?.amount?:""
        setContent {
            YespayTheme(darkTheme = false) {
                PaymentScreenUPI(onBack = { onBackPressed() }, viewModel = viewModel)
            }
        }
    }

    override fun onBackPressed() {
        ExecuteUtil.ThrowOut(this)
        super.onBackPressed()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PaymentScreenUPI(onBack:()->Unit, viewModel: UPIViewModel){
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Pay")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBack.invoke()
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
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = if(viewModel.amount.value.isEmpty()) Color.Gray else MaterialTheme.colors.primary
            ){
                Button(
                    modifier = Modifier.fillMaxSize(),
                    onClick = {
                        if(viewModel.amount.value.isNotEmpty()){
                            viewModel.makeUpiPayment(context = context)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if(viewModel.amount.value.isEmpty()) Color.Gray else MaterialTheme.colors.primary
                    )
                ) {
                    Text(
                        text = "PROCEED TO PAY",
                        color = if(viewModel.amount.value.isEmpty()) Color.DarkGray else Color.White
                    )
                }
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = LightestRed)
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            Card(
                modifier = Modifier.padding(10.dp),
                backgroundColor = Color.White,
                shape = RoundedCornerShape(15.dp),
                elevation = 6.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.size(45.dp),
                            backgroundColor = MaterialTheme.colors.primary,
                            shape = CircleShape
                        ) {
                            Box(modifier = Modifier.fillMaxSize()){
                                Text(
                                    text = viewModel.upiCredential?.name?.first()?.toString()?:"",
                                    fontSize = 26.sp,
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }

                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp)) {
                            Text(
                                text = viewModel.upiCredential?.name?:"",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Text(
                                text = viewModel.upiCredential?.upi?:"",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        value = viewModel.amount.value, onValueChange = { viewModel.amount.value = it },
                        placeholder = {
                            Text(text = "Enter Amount", style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Gray
                            ))
                        },
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                imageVector = Icons.Default.CurrencyRupee,
                                contentDescription ="rupee",
                                tint = Color.Black
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        value = viewModel.message.value, onValueChange = { viewModel.message.value = it },
                        placeholder = {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .align(CenterHorizontally)) {
                                Text(
                                    text = "Add a message (optional)", style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                ))
                            }
                        },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }
                        )
                    )
                }
            }
        }
    }
}