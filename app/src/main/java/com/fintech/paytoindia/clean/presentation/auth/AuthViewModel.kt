package com.fintech.paytoindia.clean.presentation.auth

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.fintech.paytoindia.R
import com.fintech.paytoindia.activities.HomeActivity
import com.fintech.paytoindia.clean.common.BaseViewModel
import com.fintech.paytoindia.clean.common.Resource
import com.fintech.paytoindia.clean.domain.model.ScreenState
import com.fintech.paytoindia.clean.domain.use_cases.authUseCase.AuthUseCase
import com.fintech.paytoindia.clean.presentation.navigation.AuthNavigation
import com.fintech.paytoindia.data.network.responses.RegularResponse
import com.fintech.paytoindia.util.Accessable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject constructor(
    private val authUseCase: AuthUseCase
) : BaseViewModel<RegularResponse>() {

    lateinit var navHostController: NavHostController
    val invalidAnim = R.raw.invalid

    val mobileNumber = mutableStateOf("")
    val password = mutableStateOf("")

    fun tryLogging() {
        if (Accessable.isAccessable()) {
            if (mobileNumber.value.trim().length < 10) {
                displayAnimation(invalidAnim, "Provide a valid mobile number", 3000L)
            } else {
                checkLoginAbility()
            }
        }
    }

    fun tryLoggingWithPassword(context: Context) {
        if (Accessable.isAccessable()) {
            if (password.value.trim().length < 4) {
                displayAnimation(invalidAnim, "Provide a valid Password", 3000L)
            } else {
                doMakeLogin {
                    context.startActivity(Intent(context, HomeActivity::class.java))
                }
            }
        }
    }

    private fun checkLoginAbility() {
        authUseCase.loginAbility(mobileNumber.value).onEach {
            when(it){
                is Resource.Success -> {
                    if(it.data?.status == true && it.data.response_code == 2){
                        navHostController.navigate(AuthNavigation.PASSWORD.route)
                        _state.value = ScreenState(isLoading = false)
                    }
                    else{
                        //Begin Registration here
                        navHostController.navigate(AuthNavigation.PASSWORD.route)
                        _state.value = ScreenState(isLoading = false)
                    }
                }
                is Resource.Error -> {
                    _state.value = ScreenState(error = it.message ?: "Some Error")
                }
                is Resource.Loading -> {
                    _state.value = ScreenState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun doMakeLogin(loginAction:()->Unit) {
        authUseCase.getUserLogin(mobileNumber.value, password.value).onEach {
            when(it){
                is Resource.Success -> {
                    fetchCredentials {
                        loginAction.invoke()
                    }
                }
                is Resource.Error -> {
                    _state.value = ScreenState(error = it.message ?: "Some Error")
                }
                is Resource.Loading -> {
                    _state.value = ScreenState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun fetchCredentials(loginAction:()->Unit) {
        authUseCase.getAllCredentials().onEach {
            when(it){
                is Resource.Success -> {
                    loginAction.invoke()
                    _state.value = ScreenState(isLoading = false)
                }
                is Resource.Error -> {
                    _state.value = ScreenState(error = it.message ?: "Some Error")
                }
                is Resource.Loading -> {
                    _state.value = ScreenState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}