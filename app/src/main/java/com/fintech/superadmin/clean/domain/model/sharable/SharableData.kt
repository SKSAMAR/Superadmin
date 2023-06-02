package com.fintech.superadmin.clean.domain.model.sharable

import androidx.annotation.DrawableRes
import com.fintech.superadmin.R

data class SharableData(
    @DrawableRes val logo: Int,
    val path: String
)

val listOfLogos = listOf(
    SharableData(R.drawable.whatsapp_icon, "com.whatsapp"),
    SharableData(R.drawable.facebook_icon, "com.facebook.katana"),
    SharableData(R.drawable.instagram_icon, "com.instagram.android"),
    SharableData(R.drawable.options_circle, "messenger")
)