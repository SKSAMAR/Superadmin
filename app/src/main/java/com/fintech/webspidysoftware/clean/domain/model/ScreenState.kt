package com.fintech.webspidysoftware.clean.domain.model

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ScreenState<T>(
    val isLoading: Boolean = false,
    val error: String = "",
    val receivedResponse: T? = null,
    var baseDialogVisible: MutableState<Boolean> = mutableStateOf(false),
    var content: @Composable ColumnScope.() -> Unit = {}
)
