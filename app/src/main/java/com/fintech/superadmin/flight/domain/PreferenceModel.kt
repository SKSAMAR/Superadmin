package com.fintech.superadmin.flight.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

data class PreferenceModel(
    val title: String,
    val icon: ImageVector,
    val isSelected: MutableState<Boolean> = mutableStateOf(false)
)
