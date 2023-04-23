package com.fintech.superadmin.clean.domain.repository

import com.fintech.superadmin.data.db.entities.AuthData
import com.fintech.superadmin.data.db.entities.User
import com.fintech.superadmin.data.db.entities.UserProfile
import com.fintech.superadmin.data.network.responses.RegularResponse
import com.fintech.superadmin.data.network.responses.SystemResponse
import com.fintech.superadmin.data_model.LoginModel

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