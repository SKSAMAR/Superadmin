package com.fintech.superadmin.clean.presentation.eko_dmt.component

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.fintech.superadmin.clean.presentation.commonComponent.DropDownSystem
import com.fintech.superadmin.clean.presentation.dmt.transaction.DmtTransactionActivity
import com.fintech.superadmin.clean.presentation.eko_dmt.transaction.EkoDmtTransactionActivity
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.eko.RecipientListItem


@Composable
fun EkoBeneficiaryInfo(
    senderMobile: String,
    beneficiaryItem: RecipientListItem,
    isExpandable: Boolean = true,
    viewHistory: (RecipientListItem) -> Unit = {},
    onRemove: (RecipientListItem) -> Unit = {},
    onVerify: (RecipientListItem) -> Unit = {},
    context: Context = LocalContext.current

) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.sdp, horizontal = 10.sdp)
            .background(color = MaterialTheme.colors.primaryVariant.copy(.15f))
            .clickable {
                val intent = Intent(context, EkoDmtTransactionActivity::class.java)
                intent.putExtra("number", senderMobile)
                intent.putExtra("selectedBankModel", beneficiaryItem)
                context.startActivity(intent)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.sdp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Name:",
                        fontSize = 14.textSdp,
                        modifier = Modifier.weight(.5f)
                    )
                    Text(
                        text = "" + beneficiaryItem.recipientName,
                        fontSize = 14.textSdp,
                        modifier = Modifier.weight(1.5f),
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Bank:",
                        fontSize = 14.textSdp,
                        modifier = Modifier.weight(.5f)
                    )
                    Text(
                        text = "" + beneficiaryItem.bank,
                        fontSize = 14.textSdp,
                        modifier = Modifier.weight(1.5f),
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.sdp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "A/C No:",
                        fontSize = 14.textSdp,
                        modifier = Modifier.weight(.5f)
                    )
                    Text(
                        text = "" + beneficiaryItem.account,
                        fontSize = 14.textSdp,
                        modifier = Modifier.weight(1.5f),
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (isExpandable) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            expanded = !expanded
                        },
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "more"
                )
            }

            if (expanded && isExpandable) {

                DropDownSystem(
                    list = beneficiaryOptions,
                    expanded = expanded,
                    onSelect = {
                        expanded = false
                        it?.let {
                            if (it.trim().contains("history", true)) {
                                viewHistory(beneficiaryItem)
                            } else if (it.trim().contains("verify", true)) {
                                onVerify(beneficiaryItem)
                            } else if (it.trim().contains("remove", true)) {
                                onRemove(beneficiaryItem)
                            }
                        }
                    }
                )

                /**
                DropDownDialog(list = beneficiaryOptions,
                    onCancel = { expanded = false },
                    onSelect = {
                        expanded = false
                        if (it.trim().contains("history", true)) {
                            viewHistory(beneficiaryItem)
                        } else if (it.trim().contains("verify", true)) {
                            onVerify(beneficiaryItem)
                        } else if (it.trim().contains("remove", true)) {
                            onRemove(beneficiaryItem)
                        }
                    }
                )
                **/


            }
        }
    }
}

val beneficiaryOptions = listOf(
    "View History",
    "Verify Account",
    "Remove"
)