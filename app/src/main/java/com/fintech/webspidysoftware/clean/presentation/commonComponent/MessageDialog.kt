package com.fintech.webspidysoftware.clean.presentation.commonComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import coil.compose.AsyncImage
import com.fintech.webspidysoftware.R
import com.fintech.webspidysoftware.clean.util.sdp
import com.fintech.webspidysoftware.clean.util.textSdp
import com.fintech.webspidysoftware.ui.theme.DesGreen
import com.fintech.webspidysoftware.ui.theme.DesRed
import com.fintech.webspidysoftware.ui.theme.OkColor


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SuccessMessageDialog(
    boolean: Boolean = true,
    message: String = "Success",
    onDismissRequest: (Boolean) -> Unit = {},
    onCancel: () -> Unit = {},
    onAccept: () -> Unit = {}
) {

    AlertDialog(
        modifier = Modifier.padding(horizontal = 30.sdp),
        onDismissRequest = { onCancel() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
           // //decorFitsSystemWindows = true,
            securePolicy = SecureFlagPolicy.SecureOff
        ),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Result", fontSize = 16.textSdp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = DesGreen
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "" + message, fontSize = 12.textSdp,
                textAlign = TextAlign.Center,
                color = DesGreen
            )
        },
        buttons = {
            BaseButton(
                modifier = Modifier.padding(start = 15.sdp, end = 15.sdp, bottom = 10.sdp),
                text = "OK",
                fontSize = 14.textSdp,
                onClick = {
                    onAccept()
                    onDismissRequest(true)
                }
            )
        }
    )


}



@OptIn(ExperimentalComposeUiApi::class)

@Composable
fun DialogSuccessDialog(
    boolean: Boolean = true,
    message: String = "Success",
    onDismissRequest: (Boolean) -> Unit = {},
    onCancel: () -> Unit = {},
    onAccept: () -> Unit = {}
) {

    AlertDialog(
        modifier = Modifier.padding(horizontal = 30.sdp),
        onDismissRequest = { onCancel() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            //decorFitsSystemWindows = true,
            securePolicy = SecureFlagPolicy.SecureOff
        ),
        title = null,
        text = null,
        buttons = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.sdp, horizontal = 10.sdp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Image(
                        modifier = Modifier
                            .size(15.sdp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                onCancel()
                            },
                        painter = painterResource(id = R.drawable.ic_baseline_cancel_exit),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
                Spacer(modifier = Modifier.height(30.sdp))
                AsyncImage(
                    modifier = Modifier.size(130.sdp),
                    model = R.drawable.logo,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(10.sdp))
                Text(
                    text = message,
                    color = DesGreen,
                    fontSize = 12.textSdp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.sdp))
            }
        },

        )


}


@OptIn(ExperimentalComposeUiApi::class)

@Composable
fun DialogLoadingDialog(
    boolean: Boolean = true,
    message: String = "Loading, Please wait.",
    onDismissRequest: (Boolean) -> Unit = {},
    onCancel: () -> Unit = {},
    onAccept: () -> Unit = {}
) {

    AlertDialog(
        modifier = Modifier.padding(horizontal = 30.sdp),
        onDismissRequest = { onCancel() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            //decorFitsSystemWindows = true,
            securePolicy = SecureFlagPolicy.SecureOff
        ),
        title = null,
        text = null,
        buttons = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.sdp, horizontal = 10.sdp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Image(
                        modifier = Modifier
                            .size(15.sdp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                onCancel()
                            },
                        painter = painterResource(id = R.drawable.ic_baseline_cancel_exit),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
                Spacer(modifier = Modifier.height(30.sdp))
                AsyncImage(
                    modifier = Modifier.size(130.sdp),
                    model = R.drawable.logo,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(10.sdp))
                Text(
                    text = message,
                    color = Color.Gray,
                    fontSize = 12.textSdp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.sdp))
            }
        },

        )


}


@OptIn(ExperimentalComposeUiApi::class)

@Composable
fun DialogFailureDialog(
    boolean: Boolean = true,
    message: String = "Failed",
    onDismissRequest: (Boolean) -> Unit = {},
    onCancel: () -> Unit = {},
    onAccept: () -> Unit = {}
) {

    AlertDialog(
        modifier = Modifier.padding(horizontal = 30.sdp),
        onDismissRequest = { onCancel() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            //decorFitsSystemWindows = true,
            securePolicy = SecureFlagPolicy.SecureOff
        ),
        title = null,
        text = null,
        buttons = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.sdp, horizontal = 10.sdp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Image(
                        modifier = Modifier
                            .size(15.sdp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                onCancel()
                            },
                        painter = painterResource(id = R.drawable.ic_baseline_cancel_exit),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
                Spacer(modifier = Modifier.height(30.sdp))
                AsyncImage(
                    modifier = Modifier.size(130.sdp),
                    model = R.drawable.logo,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(10.sdp))
                Text(
                    text = message,
                    color = DesRed,
                    fontSize = 12.textSdp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.sdp))
            }
        },

        )


}


@OptIn(ExperimentalComposeUiApi::class)

@Composable
fun SuccessResponseDialog(
    boolean: Boolean = true,
    title: String = "Success",
    message: String = "",
    onDismissRequest: (Boolean) -> Unit = {},
    onCancel: () -> Unit = {},
    onAccept: () -> Unit = {}
) {

    AlertDialog(
        modifier = Modifier.padding(horizontal = 30.sdp),
        onDismissRequest = { onCancel() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            //decorFitsSystemWindows = true,
            securePolicy = SecureFlagPolicy.SecureOff
        ),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Result", fontSize = 16.textSdp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = DesGreen
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "" + message, fontSize = 12.textSdp,
                textAlign = TextAlign.Center,
                color = DesGreen
            )
        },
        buttons = {
            BaseButton(
                modifier = Modifier.padding(start = 15.sdp, end = 15.sdp, bottom = 10.sdp),
                text = "OK",
                fontSize = 14.textSdp,
                onClick = {
                    onAccept()
                    onDismissRequest(true)
                }
            )
        }

    )


}


@OptIn(ExperimentalComposeUiApi::class)

@Composable
fun FailureMessageDialog(
    boolean: Boolean = true,
    message: String = "Failure",
    onDismissRequest: (Boolean) -> Unit = {},
    onCancel: () -> Unit = {},
    onAccept: () -> Unit = {}
) {

    AlertDialog(
        modifier = Modifier.padding(horizontal = 30.sdp),
        onDismissRequest = { onCancel() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            //decorFitsSystemWindows = true,
            securePolicy = SecureFlagPolicy.SecureOff
        ),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Error", fontSize = 16.textSdp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.error
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "" + message, fontSize = 12.textSdp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.error
            )
        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(start = 15.sdp, end = 15.sdp, bottom = 10.sdp)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) {
                        onAccept()
                        onDismissRequest(true)
                    }
            ) {
                Text(
                    text = "OK",
                    textAlign = TextAlign.Center,
                    color = OkColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.textSdp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

    )


}


@OptIn(ExperimentalComposeUiApi::class)

@Composable
fun FailureMessageDialogXX(
    boolean: Boolean = true,
    message: String = "Failure",
    onDismissRequest: (Boolean) -> Unit = {},
    onCancel: () -> Unit = {},
    onAccept: () -> Unit = {}
) {

    Box(modifier = Modifier.fillMaxSize()) {
        AlertDialog(
            modifier = Modifier.align(Alignment.Center),
            onDismissRequest = { onCancel() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false,
                //decorFitsSystemWindows = true,
                securePolicy = SecureFlagPolicy.SecureOff
            ),
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Error", fontSize = 16.textSdp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.error
                )
            },
            text = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "" + message, fontSize = 12.textSdp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.error
                )
            },
            buttons = {
                BaseButton(
                    modifier = Modifier.padding(start = 15.sdp, end = 15.sdp, bottom = 10.sdp),
                    text = "OK",
                    fontSize = 14.textSdp,
                    onClick = {
                        onAccept()
                        onDismissRequest(true)
                    }
                )
            }

        )
    }


}


@OptIn(ExperimentalComposeUiApi::class)

@Composable
fun LoadingMessageDialog(
    boolean: Boolean = true,
    message: String = "Please wait",
    onDismissRequest: (Boolean) -> Unit = {},
    onCancel: () -> Unit = {},
    onAccept: () -> Unit = {}
) {

    AlertDialog(
        onDismissRequest = { onCancel() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            //decorFitsSystemWindows = true,
            securePolicy = SecureFlagPolicy.SecureOff
        ),
        title = {
            Column {
                LinearProgressIndicator(
                    color = MaterialTheme.colors.primary, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.sdp)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Please wait..", fontSize = 16.textSdp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(20.sdp))
            }
        },
        buttons = {
        }
    )


}