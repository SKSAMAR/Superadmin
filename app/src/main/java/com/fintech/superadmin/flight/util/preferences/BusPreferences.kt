package com.fintech.superadmin.flight.util.preferences

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.Sledding
import androidx.compose.material.icons.filled.Snowshoeing
import androidx.compose.material.icons.filled.WindPower
import com.fintech.superadmin.flight.domain.PreferenceModel

sealed class BusPreferences(val preferenceModel: PreferenceModel) {
    object AC : BusPreferences(PreferenceModel(title = "AC", Icons.Default.Snowshoeing))
    object NonAC : BusPreferences(PreferenceModel(title = "Non AC", Icons.Default.WindPower))
    object Sleeper : BusPreferences(PreferenceModel(title = "Sleeper", Icons.Default.Sledding))
    object Seat : BusPreferences(PreferenceModel(title = "Seat", Icons.Default.Chair))
}

val preferenceList = listOf(
    BusPreferences.AC,
    BusPreferences.NonAC,
    BusPreferences.Sleeper,
    BusPreferences.Seat,
)