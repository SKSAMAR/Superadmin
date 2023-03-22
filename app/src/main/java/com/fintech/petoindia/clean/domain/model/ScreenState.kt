package com.fintech.petoindia.clean.domain.model

data class ScreenState<T>(
    val isLoading: Boolean = false,
    val error: String = "",
    val receivedResponse: T? = null
)
