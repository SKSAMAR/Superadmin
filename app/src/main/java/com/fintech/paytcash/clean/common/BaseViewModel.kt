package com.fintech.paytcash.clean.common

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintech.paytcash.clean.domain.model.AnimationModel
import com.fintech.paytcash.clean.domain.model.ScreenState
import com.fintech.paytcash.clean.presentation.commonComponent.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseViewModel<T>: ViewModel() {

    protected val _state = mutableStateOf(ScreenState<T>())
    val state: State<ScreenState<T>> = _state
    val animationState: MutableState<AnimationModel?> = mutableStateOf(null)
    var baseDialogVisible by mutableStateOf(false)
    var somethingChanged by mutableStateOf(false)

    fun displayAnimation(anim: Int, message: String, time: Long){
        viewModelScope.launch(Dispatchers.IO){
            animationState.value = AnimationModel(anim, message)
            delay(time)
            animationState.value = null
        }
    }


    fun displayDialogSuccess(message: String?) {
        _state.value.content = {
            DialogSuccessDialog(
                onDismissRequest = { _state.value.baseDialogVisible.value = false },
                onAccept = {
                    _state.value.baseDialogVisible.value = false
                },
                onCancel = {
                    _state.value.baseDialogVisible.value = false
                },
                message = message ?: ""
            )
        }
        _state.value.baseDialogVisible.value = true
    }

    fun displayDialogLoading(message: String?) {
        _state.value.baseDialogVisible.value = false
        _state.value.content = {
            DialogLoadingDialog(
                onDismissRequest = { _state.value.baseDialogVisible.value = false },
                onAccept = {
                    _state.value.baseDialogVisible.value = false
                },
                onCancel = {
                    _state.value.baseDialogVisible.value = false
                },
                message = message ?: ""
            )
        }
        _state.value.baseDialogVisible.value = true
    }

    fun displayDialogFailure(message: String?) {
        _state.value.baseDialogVisible.value = false
        _state.value.content = {
            DialogFailureDialog(
                onDismissRequest = { _state.value.baseDialogVisible.value = false },
                onAccept = {
                    _state.value.baseDialogVisible.value = false
                },
                onCancel = {
                    _state.value.baseDialogVisible.value = false
                },
                message = message ?: ""
            )
        }
        _state.value.baseDialogVisible.value = true
    }

    fun displaySuccessMessage(message: String?) {
        _state.value.baseDialogVisible.value = false
        _state.value.content = {
            SuccessMessageDialog(
                onDismissRequest = { _state.value.baseDialogVisible.value = false },
                onAccept = {
                    _state.value.baseDialogVisible.value = false
                },
                onCancel = {
                    _state.value.baseDialogVisible.value = false
                },
                message = message ?: ""
            )
        }
        _state.value.baseDialogVisible.value = true
    }

    fun displayResponseMessage(message: String?) {
        _state.value.content = {
            SuccessResponseDialog(
                onDismissRequest = { _state.value.baseDialogVisible.value = false },
                onAccept = {
                    _state.value.baseDialogVisible.value = false
                },
                onCancel = {
                    _state.value.baseDialogVisible.value = false
                },
                message = message ?: ""
            )
        }
        _state.value.baseDialogVisible.value = true
    }

    fun displayFailureMessage(message: String?) {
        _state.value.content = {
            FailureMessageDialog(
                onDismissRequest = { _state.value.baseDialogVisible.value = false },
                onAccept = {
                    _state.value.baseDialogVisible.value = false
                },
                onCancel = {
                    _state.value.baseDialogVisible.value = false
                },
                message = message ?: ""
            )
        }
        _state.value.baseDialogVisible.value = true
    }

    fun displayLoading(message: String? = "Please wait") {
        _state.value.content = {
            LoadingMessageDialog(
                onDismissRequest = { _state.value.baseDialogVisible.value = false },
                onAccept = {
                    _state.value.baseDialogVisible.value = false
                },
                onCancel = {
                    _state.value.baseDialogVisible.value = false
                },
                message = message ?: ""
            )
        }
        _state.value.baseDialogVisible.value = true
    }

    fun dismissDialog() {
        _state.value.baseDialogVisible.value = false
    }

}