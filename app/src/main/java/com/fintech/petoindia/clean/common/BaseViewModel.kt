package com.fintech.petoindia.clean.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintech.petoindia.clean.domain.model.AnimationModel
import com.fintech.petoindia.clean.domain.model.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel<T>: ViewModel() {

    protected val _state = mutableStateOf(ScreenState<T>())
    val state: State<ScreenState<T>> = _state
    val animationState: MutableState<AnimationModel?> = mutableStateOf(null)

    fun displayAnimation(anim: Int, message: String, time: Long){
        viewModelScope.launch(Dispatchers.IO){
            animationState.value = AnimationModel(anim, message)
            delay(time)
            animationState.value = null
        }
    }
}