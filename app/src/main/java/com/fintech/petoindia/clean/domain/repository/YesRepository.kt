package com.fintech.petoindia.clean.domain.repository

import com.fintech.petoindia.data.db.entities.AuthData
import com.fintech.petoindia.data.db.entities.User
import com.fintech.petoindia.data.db.entities.UserProfile
import com.fintech.petoindia.data.network.responses.RegularResponse
import com.fintech.petoindia.data.network.responses.SystemResponse
import com.fintech.petoindia.data_model.LoginModel

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