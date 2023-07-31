package com.fintech.superadmin.clean.presentation.payQr

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.flight.util.common.Constant
import com.fintech.superadmin.ui.theme.BackgroundHomeColor
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.util.captureScreen
import com.fintech.superadmin.util.shareScreen
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowQrActivity : BaseComponentAct() {

    var bitMap by mutableStateOf<Bitmap?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializers()
        setContent {
            SuperAdminTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    QrContent()
                }
            }
        }
    }

    @Composable
    private fun QrContent() {
        BaseScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Show QR")
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressedDispatcher.onBackPressed() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "arrow_back"
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                val view = LocalView.current
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.5f)
                        .background(color = BackgroundHomeColor),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(.8f),
                        model = bitMap,
                        contentDescription = "qr code"
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.sdp, horizontal = 10.sdp)
                ) {

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                captureScreen(view)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(30.sdp),
                            painter = painterResource(id = R.drawable.download_img),
                            contentDescription = "download"
                        )
                        Text(text = "Download this QR Code", fontSize = 12.textSdp)
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                shareScreen(view)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(30.sdp),
                            painter = painterResource(id = R.drawable.share_img),
                            contentDescription = "share"
                        )
                        Text(text = "Share this QR Code", fontSize = 12.textSdp)
                    }

                }

            }

        }
    }


    private fun initializers(){
        val url = intent.getStringExtra(Constant.QR_CODE)?:""
        val multiFormatWriter =  MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE,200,200);
            val barcodeEncoder = BarcodeEncoder()
            bitMap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (e: WriterException) {
            e.printStackTrace();
        }
    }


}