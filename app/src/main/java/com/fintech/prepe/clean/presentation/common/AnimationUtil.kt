package com.fintech.prepe.clean.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fintech.prepe.clean.domain.model.AnimationModel

@Composable
fun ProblemAnimation(
    modifier: Modifier = Modifier,
    animationModel: AnimationModel?,
    size: Dp = 200.dp
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        animationModel?.animation?.let { anim ->
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(anim))
            LottieAnimation(
                composition,
                modifier = modifier
                    .size(size)
                    .padding(vertical = 10.dp)
                    .clip(RectangleShape),
                contentScale = ContentScale.Crop,
                restartOnPlay = true,
                iterations = LottieConstants.IterateForever,
                alignment = Alignment.Center,
            )
        }
        animationModel?.message?.let { error ->
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = error,
                color = Color.Red,
                fontSize = 14.sp,
                style = TextStyle(textAlign = TextAlign.Center),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ScreenAnimation(
    modifier: Modifier = Modifier.fillMaxSize(),
    isAnimatable: Boolean = true,
    resources: Int = com.fintech.prepe.R.raw.not_found
){
    if(isAnimatable){
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resources))
        LottieAnimation(
            composition,
            modifier = modifier,
            contentScale = ContentScale.Inside,
            restartOnPlay = true,
            iterations = LottieConstants.IterateForever
        )
    }
}