package com.fintech.superadmin.clean.presentation.qr

import android.graphics.Bitmap
import android.os.Bundle
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import coil.compose.AsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.presentation.common.BasicScreenState
import com.fintech.superadmin.clean.presentation.wallet.WalletBalanceViewModel
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QrActivity : BaseComponentAct() {

    val viewModel by viewModels<WalletBalanceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    QrScreenContent()
                }
            }
        }
    }


    @Composable
    private fun QrScreenContent() {
        val user = viewModel.state.value.receivedResponse?.observeAsState()
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "My QR Code")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBackPressedDispatcher.onBackPressed()
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
            }
        ) {
            val config = LocalConfiguration.current
            BasicScreenState(state = viewModel.state) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.sdp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(18.sdp))
                    }

                    viewModel.state.value.receivedResponse?.let { dto ->
                        val url =
                            "${applicationContext.packageName}?mode=scanPay&mobile=${user?.value?.mobile?.trim()}&id=${user?.value?.id}&name=$${user?.value?.name}"
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 14.sdp, horizontal = 12.sdp),
                                shape = RoundedCornerShape(10.sdp),
                                elevation = 5.sdp
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.sdp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    url.let {

                                        val width = config.screenWidthDp
                                        val height = config.screenHeightDp
                                        var dimen = if (width < height) width else height
                                        dimen = dimen * 3 / 4
                                        val bitmap: Bitmap
                                        val qrgEncoder =
                                            QRGEncoder(url, null, QRGContents.Type.TEXT, dimen)
                                        bitmap = qrgEncoder.encodeAsBitmap()

                                        AsyncImage(
                                            modifier = Modifier.size(190.sdp),
                                            model = bitmap,
                                            contentDescription = "my_qr",
                                            contentScale = ContentScale.Fit
                                        )
                                        Spacer(modifier = Modifier.height(5.sdp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Card(
                                                modifier = Modifier.size(30.sdp)
                                            ) {
                                                AsyncImage(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(5.sdp),
                                                    model = R.drawable.logo,
                                                    contentDescription = "my_qr",

                                                    )
                                            }
                                            Spacer(modifier = Modifier.width(10.sdp))
                                            Text(
                                                text = user?.value?.mobile ?: "",
                                                fontSize = 12.textSdp
                                            )
                                            Spacer(modifier = Modifier.width(10.sdp))
                                        }

                                    }
                                }
                            }
                        }

                    }

                }
            }
        }
    }
}