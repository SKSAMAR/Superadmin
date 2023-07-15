package com.fintech.superadmin.clean.presentation.suvidhaPayout.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fintech.superadmin.clean.data.remote.dto.cashfree.PayoutBeneficiary
import com.fintech.superadmin.clean.data.remote.dto.suvidhaPayout.SuvidhaBeneficiary
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SuvidhaBeneficiaryData(data: SuvidhaBeneficiary, onClick:()->Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 5.sdp, horizontal = 12.sdp),
        backgroundColor = MaterialTheme.colors.primary.copy(.15f),
        elevation = 0.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.sdp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.sdp)
            ) {
                Text(
                    text = data.NAME ?: "",
                    fontSize = 12.textSdp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.sdp)
            ) {
                Text(
                    text = data.ACCOUNT ?: "",
                    fontSize = 12.textSdp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = data.IFSC ?: "",
                    fontSize = 12.textSdp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}