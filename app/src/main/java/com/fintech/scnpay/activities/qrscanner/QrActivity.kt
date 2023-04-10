package com.fintech.scnpay.activities.qrscanner

import android.content.Intent
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.fintech.scnpay.activities.qrscanner.util.QrCodeAnalyzer
import com.fintech.scnpay.activities.upi.PayWithUPI
import com.fintech.scnpay.data.qrPayement.UPICredential
import com.fintech.scnpay.ui.theme.YespayTheme
import com.fintech.scnpay.util.Accessable
import com.fintech.scnpay.util.Constant
import com.fintech.scnpay.util.ViewUtils

class QrActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YespayTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    QRScreen(onBack = {onBackPressed()})
                }
            }
        }
    }

    @Composable
    private fun QRScreen( onBack:()->Unit ){
        var code by remember {
            mutableStateOf("")
        }
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val cameraProviderFuture = remember {
            ProcessCameraProvider.getInstance(context)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Scan & Pay")
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
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AndroidView(
                    factory = { context ->
                        val previewView = PreviewView(context)
                        val preview = Preview.Builder().build()
                        val selector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()
                        preview.setSurfaceProvider(previewView.surfaceProvider)
                        val imageAnalysis = ImageAnalysis.Builder()
                            .setTargetResolution(
                                Size(
                                    previewView.width,
                                    previewView.height
                                )
                            )
                            .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                            .build()
                        imageAnalysis.setAnalyzer(
                            ContextCompat.getMainExecutor(context),
                            QrCodeAnalyzer { result ->
                                code = result
                                if(code.isNotEmpty()){
                                    val data = ViewUtils.splitQuery(code)
                                    data?.let {
                                        val upiDetails =  UPICredential(
                                            upi = data["upi://pay?pa"],
                                            name = data["pn"],
                                            mc = data["mc"],
                                            mode = data["mode"],
                                            purpose = data["purpose"],
                                            fullUrl = code
                                        ).also {
                                            if(it.upi.isNullOrEmpty()){
                                                it.upi = data["pa"]
                                            }
                                            if(it.name.isNullOrEmpty()){
                                                it.name = data["upi://pay?pn"]
                                            }
                                            if(data.containsKey("am")){
                                              it.amount = data["am"]
                                            }
                                        }
                                        if(data.containsKey("upi://pay?pa") && Accessable.isAccessable()){
                                            val intent = Intent(context, PayWithUPI::class.java)
                                            intent.putExtra(Constant.UPI_CREDENTIALS, upiDetails)
                                            context.startActivity(intent)
                                            finish()
                                        }
                                        else if(data.containsKey("pa") && Accessable.isAccessable()){
                                            val intent = Intent(context, PayWithUPI::class.java)
                                            intent.putExtra(Constant.UPI_CREDENTIALS, upiDetails)
                                            context.startActivity(intent)
                                            finish()
                                        }
                                    }
                                }

                            }
                        )
                        try {
                            cameraProviderFuture.get().bindToLifecycle(
                                lifecycleOwner,
                                selector,
                                preview,
                                imageAnalysis
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        previewView
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
