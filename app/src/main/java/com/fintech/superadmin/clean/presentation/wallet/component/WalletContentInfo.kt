package com.fintech.superadmin.clean.presentation.wallet.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.fintech.superadmin.R
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp
import com.fintech.superadmin.data.db.entities.User

@Composable
fun WalletContentInfo(
    user: State<User?>?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(20.sdp))
        AsyncImage(
            modifier = Modifier.size(120.sdp),
            model = R.drawable.correct,
            contentDescription = "available_balance",
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(20.sdp))
        Text(
            text = "Available Balance fetched\nSuccessfully",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.textSdp
        )

        Spacer(modifier = Modifier.height(10.sdp))

        Text(
            text = "Available Balance",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            fontSize = 12.textSdp
        )

        Text(
            text = stringResource(id = R.string.rupee_sign) + user?.value?.mainbalance,
            fontSize = 28.textSdp,
        )
        Spacer(modifier = Modifier.height(20.sdp))
    }
}