package com.fintech.superadmin.flight.presentation.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fintech.superadmin.R

@Composable
fun ScreenAnimation(
    modifier: Modifier = Modifier.fillMaxSize(),
    isAnimating: Boolean = true,
    resources: Int = R.raw.not_found
){

    if(isAnimating){
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resources))
        LottieAnimation(
            composition,
            modifier = modifier,
            contentScale = ContentScale.Inside,
            restartOnPlay = true,
            iterations = LottieConstants.IterateForever,
        )
    }
}