package com.fintech.superadmin.activities.qrscanner

import android.Manifest
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.fintech.superadmin.R
import com.fintech.superadmin.activities.mobilenumber.SendMoney
import com.fintech.superadmin.activities.qrscanner.util.QrCodeAnalyzer
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.db.AppDatabase
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.ui.theme.SuperAdminTheme
import com.fintech.superadmin.util.Accessable
import com.fintech.superadmin.util.ViewUtils
import com.fintech.superadmin.util.displayMobilePayQr
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class QrMobilePayActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperAdminTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    QRScreen(
                        onBack = {
                            onBackPressedDispatcher.onBackPressed()
                        }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
    @Composable
    private fun QRScreen(onBack: () -> Unit) {
        var code by remember {
            mutableStateOf("")
        }
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val cameraProviderFuture = remember {
            ProcessCameraProvider.getInstance(context)
        }

        val notificationPermission = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.CAMERA,
            )
        )

        if (notificationPermission.allPermissionsGranted) {
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
                                onClick = {
                                    if (Accessable.isAccessable()) {
                                        val user: User =
                                            AppDatabase.getAppDatabase(this@QrMobilePayActivity).userDao.regularUser
                                        displayMobilePayQr(user, this@QrMobilePayActivity)
                                    }

                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.QrCode,
                                    contentDescription = "help",
                                )
                            }
                        }
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
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
                                        if (code.isNotEmpty()) {
                                            val data = ViewUtils.splitQuery(code)
                                            data?.let {
                                                val mobile = data["mobile"]
                                                val name = data["name"]
                                                val id = data["id"]

                                                mobile?.let {
                                                    if (Accessable.isAccessable()) {
                                                        val intent = Intent(
                                                            this@QrMobilePayActivity,
                                                            SendMoney::class.java
                                                        )
                                                        intent.putExtra(
                                                            "receiver_id",
                                                            id
                                                        )
                                                        intent.putExtra(
                                                            "receiver_name",
                                                            name
                                                        )
                                                        intent.putExtra(
                                                            "receiver_mobile",
                                                            mobile
                                                        )
                                                        startActivity(intent)
                                                        finish()
                                                    }
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
                            modifier = Modifier.fillMaxSize()
                        )
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            shape = CutOutShape(),
                            color = Color.Black.copy(alpha = 0.45f)
                        ) {

                        }
                    }
                }
            }
        } else {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(20.dp),
                    painter = painterResource(id = R.drawable.shield),
                    contentDescription = "permission_image",
                    contentScale = ContentScale.Fit
                )
                Card(
                    onClick = {
                        notificationPermission.launchMultiplePermissionRequest()
                    },
                    modifier = Modifier
                        .height(28.sdp),
                    backgroundColor = MaterialTheme.colors.surface,
                    border = BorderStroke(
                        width = 1.sdp,
                        color = MaterialTheme.colors.surface
                    ),
                    elevation = 0.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            text = "ALLOW PERMISSION",
                            color = MaterialTheme.colors.onSurface,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.textSdp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    class CutOutShape : Shape {
        override fun createOutline(
            size: androidx.compose.ui.geometry.Size,
            layoutDirection: androidx.compose.ui.unit.LayoutDirection,
            density: Density
        ): Outline {
            val outlinePath = Path()
            outlinePath.addRect(Rect(Offset(0f, 0f), size))

            val cutoutHeight = size.height * 0.4f
            val cutoutWidth = size.width * 0.75f
            val center = Offset(size.width / 2f, size.height / 2f)

            val cutoutPath = Path()
            cutoutPath.addRoundRect(
                RoundRect(
                    Rect(
                        topLeft = center - Offset(
                            cutoutWidth / 2f,
                            cutoutHeight / 2f
                        ),
                        bottomRight = center + Offset(
                            cutoutWidth / 2f,
                            cutoutHeight / 2f
                        )
                    ),
                    cornerRadius = CornerRadius(16f, 16f)
                )
            )

            val finalPath = Path.combine(
                PathOperation.Difference,
                outlinePath,
                cutoutPath
            )

            return Outline.Generic(finalPath)
        }
    }
}


