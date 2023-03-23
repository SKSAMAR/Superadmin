package com.fintech.payware.auth.data

import com.fintech.payware.data.db.entities.AuthData
import com.fintech.payware.data.network.responses.RegularResponse
import com.fintech.payware.data.network.responses.SystemResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("Backend/Merchant/MemberOperation/Login/Login.php")
    fun getUserLogin(
        @Field("mobile") mobileX: String?,
        @Field("password") password: String?,
        @Field("long") longitude: String?,
        @Field("lati") latitude: String?
    ): Observable<AuthData>


    //OnBoarding
    @FormUrlEncoded
    @POST("Backend/Merchant/MemberOperation/temp/onboard.php")
    fun sendBoardOTP(
        @Field("mobile") mobile: String,
        @Field("email") email: String,
        @Field("sendOTP") sendOTP: String = "sendOTP",
    ): Observable<SystemResponse<String?>>


    @FormUrlEncoded
    @POST("Backend/Merchant/MemberOperation/temp/onboard.php")
    fun doOnBoard(
        @Field("mobile") mobile: String,
        @Field("email") email: String,
        @Field("fname") fname: String,
        @Field("gstNumber") gstNumber: String,
        @Field("referral_code") referral_code: String,
        @Field("password") password: String,
        @Field("hash") hash: String,
        @Field("otp") otp: String,
    ): Observable<RegularResponse>

    @FormUrlEncoded
    @POST("Backend/Merchant/MemberOperation/temp/onboard.php")
    fun forgotPassword(
        @Field("credential") credential: String,
        @Field("sendOTP") sendOTP: String = "forgotOTP",
    ): Observable<SystemResponse<String?>>

    @FormUrlEncoded
    @POST("Backend/Merchant/MemberOperation/temp/onboard.php")
    fun changePassword(
        @Field("credential") credential: String,
        @Field("password") password: String,
        @Field("hash") hash: String,
        @Field("otp") otp: String,
        @Field("changePassword") changePassword: String = "changePassword",
    ): Observable<RegularResponse>

}