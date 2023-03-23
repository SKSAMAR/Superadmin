package com.fintech.payware.clean.presentation.navigation

sealed class AuthNavigation(val route: String){
    object CREDENTIAL : AuthNavigation("CREDENTIAL")
    object PASSWORD : AuthNavigation("PASSWORD")
    object REGISTRATION : AuthNavigation("REGISTRATION")
}
