package com.fintech.superadmin.clean.presentation.referEarn.component

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.domain.model.contact.Contact
import com.fintech.superadmin.clean.util.ViewUtils.sendSms
import com.fintech.superadmin.clean.util.ViewUtils.shareWithWhatsApp
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.ui.theme.BackgroundCardGrey

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactRow(
    invitationMessage: String,
    contact: Contact,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    location: Int = 0
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = BackgroundCardGrey(),
        elevation = 0.dp,
        shape = RoundedCornerShape(
            topStart = if (location == 1) 8.sdp else 0.dp,
            topEnd = if (location == 1) 8.sdp else 0.dp,
            bottomStart = if (location == 2) 8.sdp else 0.dp,
            bottomEnd = if (location == 2) 8.sdp else 0.dp,
        )
    ) {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.sdp, vertical = 15.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = modifier.weight(1.2f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(30.sdp),
                        model = R.drawable.contact_icon,
                        contentDescription = "circleShape",
                    )

                    Spacer(modifier = Modifier.width(8.sdp))

                    Column {
                        Text(
                            text = contact.name ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = contact.phoneNumber ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Row(
                    modifier = modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    if (contact.isAvailableOnWhatsApp == true) {
                        AsyncImage(
                            model = R.drawable.whatsapp_icon,
                            contentDescription = "invite_with whatsapp",
                            modifier = Modifier
                                .size(20.sdp)
                                .clickable(
                                    indication = null,
                                    interactionSource = MutableInteractionSource()
                                ) {
                                    context.shareWithWhatsApp(
                                        to = contact.phoneNumber ?: "",
                                        message = "${contact.name}, I invite you to join ${
                                            context.appName()
                                        } click on the link and join today $invitationMessage"
                                    )
                                }
                        )
                        Spacer(modifier = Modifier.width(10.sdp))
                    }

                    Card(
                        backgroundColor = MaterialTheme.colors.primary,
                        onClick = {
                            context.sendSms(
                                to = contact.phoneNumber ?: "",
                                message = "${contact.name}, I invite you to join ${
                                    context.appName()
                                } click on the link and join today $invitationMessage"
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 10.sdp, vertical = 4.sdp)
                        ) {
                            Text(
                                text = "Invite Now",
                                color = MaterialTheme.colors.surface,
                                fontSize = 9.textSdp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                }
            }

            if (location == 1 || location == 0) {
                Divider()
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactRow(
    contact: Contact,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    location: Int = 0,
    onSelect: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = BackgroundCardGrey(),
        elevation = 0.dp,
        shape = RoundedCornerShape(
            topStart = if (location == 1) 8.sdp else 0.dp,
            topEnd = if (location == 1) 8.sdp else 0.dp,
            bottomStart = if (location == 2) 8.sdp else 0.dp,
            bottomEnd = if (location == 2) 8.sdp else 0.dp,
        ),
        onClick = {
            onSelect(contact.phoneNumber ?: "")
        }
    ) {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.sdp, vertical = 15.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = modifier.weight(1.2f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(30.sdp),
                        model = R.drawable.contact_icon,
                        contentDescription = "circleShape",
                    )

                    Spacer(modifier = Modifier.width(8.sdp))

                    Column {
                        Text(
                            text = contact.name ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = contact.phoneNumber ?: "",
                            fontSize = 10.textSdp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            if (location == 1 || location == 0) {
                Divider()
            }
        }
    }
}

fun Context.appName(): String? {
    val packageManager: PackageManager = applicationContext.packageManager
    val applicationInfo =
        packageManager.getApplicationInfo(applicationContext.packageName, 0)
    return packageManager.getApplicationLabel(applicationInfo) as String?
}