package com.fintech.superadmin.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.viewmodel.VirtualViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VirtualHomeActivity : BaseComponentAct() {

    val viewModel by viewModels<VirtualViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getVirtualAccount(this@VirtualHomeActivity)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    VirtualHomeScreen()
                }
            }
        }
    }

    @Composable
    private fun VirtualHomeScreen() {
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Virtual Account") },
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
            viewModel.state.value.receivedResponse?.let { acc ->
                viewModel.user?.let { user ->
                    LaunchedEffect(key1 = viewModel.state.value){
                        viewModel.state.value.receivedResponse?.vPA_ADDRESS?.let {
                            viewModel.bitmap = viewModel.getQrCode(windowManager,
                                "upi://pay?pa=$it&pn=Rakeshpay"
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            viewModel.bitmap?.let{bitmap->
                                AsyncImage(
                                    modifier = Modifier
                                        .size(200.sdp)
                                        .padding(vertical = 1.sdp),
                                    model = bitmap,
                                    contentDescription = "qr_code",
                                )
                            }
                            Text(
                                text = "UPI ID: "+acc.vPA_ADDRESS,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 1.sdp),
                                textAlign = TextAlign.Center,
                                fontSize = 12.textSdp
                            )
                            Text(
                                text = "Account: "+acc.rECIEVEDACC,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 1.sdp),
                                textAlign = TextAlign.Center,
                                fontSize = 12.textSdp
                            )
                            Text(
                                text = "IFSC: "+acc.rECIEVEDIFSC,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 1.sdp),
                                textAlign = TextAlign.Center,
                                fontSize = 12.textSdp
                            )

                            Text(
                                text = buildAnnotatedString {
                                    append(user.name?:"")
                                    append(" ")
                                    append(user.lastname?:"")
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.sdp),
                                textAlign = TextAlign.Center,
                                fontSize = 15.textSdp
                            )

                            Text(
                                text = ""+user.email,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 1.sdp),
                                textAlign = TextAlign.Center,
                                fontSize = 12.textSdp
                            )
                            Text(
                                text = ""+user.mobile,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 1.sdp),
                                textAlign = TextAlign.Center,
                                fontSize = 12.textSdp
                            )
                        }
                    }
                }
            }
        }
    }

}