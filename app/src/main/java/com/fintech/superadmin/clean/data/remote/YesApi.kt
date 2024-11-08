package com.fintech.superadmin.clean.data.remote

import com.fintech.superadmin.data.db.entities.AuthData
import com.fintech.superadmin.data.network.responses.RegularResponse
import com.fintech.superadmin.data.network.responses.SystemResponse
import com.fintech.superadmin.data_model.LoginModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface YesApi {

    @FormUrlEncoded
    @POST("DashBoard/Agent/Backend/Login/Login.php")
    suspend fun loginAbility(
        @Field("mobile") mobile: String,
        @Field("loginAbility") loginAbility: String = "loginAbility"
    ): RegularResponse


    @FormUrlEncoded
    @POST("DashBoard/Agent/Backend/Login/Login.php")
    suspend fun getUserLogin(
        @Field("mobile") mobile: String,
        @Field("password") password: String,
    ): AuthData

    @FormUrlEncoded
    @POST("DashBoard/Agent/Backend/displays/userFetch.php")
    suspend fun getAllCredentials(
        @Field("fetch_all") fetch_all: String
    ): SystemResponse<LoginModel>



}