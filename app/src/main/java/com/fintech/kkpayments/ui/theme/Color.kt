package com.fintech.kkpayments.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFF5b34a0)
val Purple500 = Color(0xFF5b34a0)
val Purple700 = Color(0xFF5b34a0)
val Teal200 = Color(0xFF03DAC5)
val IconColor = Color(0xFF5b34a0)

val LightestRed = Color(0xFFFFF3F1)
val MyRed = Color(0xFFF44336)
val MyGreen = Color(0xFF009C06)
val IconColorLightest = Color(0xFF55E0E9)
val LightestGrey = Color(0xFFECECEC)
val FarLightestGrey = Color(0xFFF3F3F3)
val LightestGreyDark = Color(0xFFC4C4C4)

val VeryLightestGrey = Color(0xFFF5F5F5)
val StarActiveColor = Color(0xffffd740)
val LightestSky = Color(0xFFF2FEFF)
val Sky = Color(0xFF2196F3)

val LighterGray = Color(0xFFF1F1F1)

val GrayishBackground = Color(0xFFe3e9ed)
val DetailingBackground = Color(0xFFf7f6fe)

val BackgroundHomeColor = Color(0xFFF7F8E8)
val BackgroundGrey = Color(0xFFD9D9D9)
val TextBlue = Color(0xFF095FDF)
val ButtonYellow = Color(0xFFE3B448)
val DesGreen = Color(0xFF3A6B35)
val ButtonGreen = Color(0xFF18B828)

//val BackgroundCardGrey = Color(0xFFEEEEEE)

val DesRed = Color(0xFFEB1717)
val OffColorRed = Color(0xFFAF1919)
val OldPriceRed = Color(0xFFE31D1D)
val StripGray = Color(0xFFE7E7E7)
val OkColor = Color(0xFF07B6BC)
//val LightestSky = Color(0xFFB6DFFF)

val OpenGreen = Color(0xFF34A853)

@Composable
fun BackgroundCardGrey(): Color {
    if (isSystemInDarkTheme()) {
        //   return Color(0xFF464646)
    }
    return Color(0xFFF2F2F2)
}