package com.fintech.superadmin.clean.presentation.rewards.component

import com.fintech.superadmin.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.fintech.superadmin.clean.data.remote.dto.reward.ScratchCardModel
import com.fintech.superadmin.clean.util.sdp
import com.fintech.superadmin.clean.util.textSdp

@Composable
fun RewardCard(
    model: ScratchCardModel,
    onClick:()->Unit
) {
    Column(
        modifier = Modifier
            .size(width = 74.sdp, height = 82.sdp)
            .padding(vertical = 5.sdp),
    ) {

        if (model.sCRATCHSTATUS.value == 1) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = R.drawable.scratched_card,
                    contentDescription = "reward"
                )
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.rupee_sign) + model.aMOUNT,
                    fontSize = 12.textSdp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize().clickable {
                    onClick()
                }
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = R.drawable.scratchless_card,
                    contentDescription = "reward"
                )
                Column(
                    modifier = Modifier
                        .padding(bottom = 4.sdp)
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Expiry: ${model.eXPIRYDATE?.replaceAfter(" ", "") ?: "Tomorrow"}",
                        fontSize = 6.textSdp,
                    )
                }
            }
        }

    }
}