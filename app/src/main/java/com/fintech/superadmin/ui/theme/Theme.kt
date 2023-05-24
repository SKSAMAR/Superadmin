package com.fintech.superadmin.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


object MyColors {
    var primary by mutableStateOf(Color(red = 37, green = 150, blue = 190))
    var secondary by mutableStateOf(Color(red = 37, green = 150, blue = 190))

    fun setColorsAgain(red: Int, green: Int, blue: Int){
        primary = Color(red = red, green = green, blue = blue)
        secondary = Color(red = red, green = green, blue = blue)
    }
}

var DarkColorPalette = darkColors(
    primary = MyColors.primary,
    primaryVariant = MyColors.primary,
    secondary = Teal200
)

var LightColorPalette = lightColors(
    primary = MyColors.primary,
    primaryVariant = MyColors.primary,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SuperAdminTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}