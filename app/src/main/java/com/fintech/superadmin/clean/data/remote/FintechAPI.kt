package com.fintech.superadmin.clean.data.remote

import com.fintech.superadmin.clean.data.remote.dto.otps.OTPResponse
import com.fintech.superadmin.clean.data.remote.dto.refer.ReferDto
import com.fintech.superadmin.clean.data.remote.dto.reward.ScratchCardData
import com.fintech.superadmin.data.network.responses.DMTSendAmountResponse
import com.fintech.superadmin.data.network.responses.RegularResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FintechAPI {


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    fun sendAmountDMT(
        @Field("bene_id") bene_id: String,
        @Field("send_amount") send_amount: String,
        @Field("send_am_acc") send_am_acc: String,
        @Field("txn_type") txn_type: String,
        @Field("ifsc") ifsc: String,
        @Field("senderMobile") senderMobile: String,
        @Field("smhash_code") smhash_code: String,
        @Field("agentOTP") agentOTP: String
    ): Observable<DMTSendAmountResponse>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    fun sendAmountOTP(
        @Field("otpSendTime") otpSendTime: String,
        @Field("send_am") send_am: String,
        @Field("sendotp") sendotp: String = "sendotp"
    ): Observable<OTPResponse>

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    fun earnedAndData(
        @Field("referData") referData: String = "referData"
    ): Observable<ReferDto>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    fun getScratchCardData(
        @Field("indexing") indexing: String?,
        @Field("trans_type") trans_type: String?,
        @Field("rewards") rewards: String = "rewards"
    ): Observable<ArrayList<ScratchCardData>>

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/operation.php")
    fun scratchTheCardData(@Field("coupon_id") coupon_id: String): Observable<RegularResponse>




}