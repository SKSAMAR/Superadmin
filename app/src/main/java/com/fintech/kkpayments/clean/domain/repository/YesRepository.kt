package com.fintech.kkpayments.clean.domain.repository

import com.fintech.kkpayments.data.db.entities.AuthData
import com.fintech.kkpayments.data.db.entities.User
import com.fintech.kkpayments.data.db.entities.UserProfile
import com.fintech.kkpayments.data.network.responses.RegularResponse
import com.fintech.kkpayments.data.network.responses.SystemResponse
import com.fintech.kkpayments.data_model.LoginModel

interface YesRepository {
    suspend fun loginAbility(
        mobile: String
    ): RegularResponse

    suspend fun getUserLogin(
        mobile: String,
        password: String,
    ): AuthData

    suspend fun getAllCredentials(
        fetch_all: String
    ): SystemResponse<LoginModel>

    suspend fun insertAuthData(
        authData: AuthData
    ): Long

    suspend fun insertUser(
        user: User
    ): Long

    suspend fun insertUserProfile(
        userProfile: UserProfile
    ): Long

}