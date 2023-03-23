package com.fintech.petoindia.clean.presentation.commonComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.fintech.petoindia.clean.util.sdp
import com.fintech.petoindia.clean.util.textSdp

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    text: String = "",
    fontSize: TextUnit = 17.textSdp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.sdp))
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(6.sdp)
            )
            .clickable { onClick() },
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 10.sdp),
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}


@Composable
fun BaseButtonThemed(
    modifier: Modifier = Modifier,
    text: String = "",
    fontSize: TextUnit = 17.textSdp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.sdp))
            .background(
                MaterialTheme.colors.primary,
                shape = RoundedCornerShape(4.sdp)
            )
            .clickable { onClick() },
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 10.sdp),
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}


@Composable
fun BaseButtonRed(
    modifier: Modifier = Modifier,
    text: String = "",
    fontSize: TextUnit = 17.textSdp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.sdp))
            .background(
                color = Color.Red,
                shape = RoundedCornerShape(6.sdp)
            )
            .clickable { onClick() },
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 10.sdp),
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    text: String = "",
    fontSize: TextUnit = 17.textSdp,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 0.dp,
        border = BorderStroke(
            width = 1.sdp, color = MaterialTheme.colors.onSurface
        ),
        shape = RoundedCornerShape(6.sdp),
        onClick = {
            onClick()
        }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = text,
                fontSize = fontSize,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 10.sdp),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OutlinedButtonBase(
    modifier: Modifier = Modifier,
    text: String = "",
    fontSize: TextUnit = 17.textSdp,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 0.dp,
        border = BorderStroke(
            width = .85.dp, color = MaterialTheme.colors.onSurface
        ),
        shape = RoundedCornerShape(4.sdp),
        onClick = {
            onClick()
        },
        backgroundColor = Color.Transparent,
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = text,
                fontSize = fontSize,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 10.sdp),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}


@Composable
fun ModifiedButton(
    modifier: Modifier = Modifier,
    text: String = "",
    fontSize: TextUnit = 17.textSdp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.sdp))
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(6.sdp)
            )
            .clickable { onClick() },
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 7.sdp, horizontal = 20.sdp),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.surface
        )
    }
}