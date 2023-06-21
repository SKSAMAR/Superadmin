package com.fintech.superadmin.clean.data.remote

import com.fintech.superadmin.clean.data.remote.dto.cashfree.CashFree
import com.fintech.superadmin.clean.data.remote.dto.cashfree.PayoutBeneficiary
import com.fintech.superadmin.clean.data.remote.dto.otps.OTPResponse
import com.fintech.superadmin.clean.data.remote.dto.refer.ReferDto
import com.fintech.superadmin.clean.data.remote.dto.reward.ScratchCardData
import com.fintech.superadmin.data.network.responses.DMTSendAmountResponse
import com.fintech.superadmin.data.network.responses.RegularResponse
import com.fintech.superadmin.data.network.responses.SystemResponse
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



    //X-PAYOUT

    @FormUrlEncoded
    @POST("Backend/Merchant/API/Payout/CashFree/DirectPayout/main.php")
    fun addPayoutBeneficiaries(
        @Field("beneName") beneName: String,
        @Field("beneEmail") beneEmail: String,
        @Field("beneMobile") beneMobile: String,
        @Field("beneAcc") beneAcc: String,
        @Field("beneIFSC") beneIFSC: String,
        @Field("address") address: String,
        @Field("nameAtBank") nameAtBank: String = "nameAtBank",
    ): Observable<CashFree>

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    fun getPayoutBeneficiaries(
        @Field("payout_account_disp") payout_account_disp: String = "payout_account_disp",
    ): Observable<SystemResponse<List<PayoutBeneficiary>?>>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/Payout/CashFree/DirectPayout/main.php")
    fun doDirectPayout(
        @Field("send_amount") send_amount: String,
        @Field("bene_id") bene_id: String,
        @Field("trans_mode") trans_mode: String,
        @Field("verify") verify: String,
        @Field("otp") otp: String,
    ): Observable<RegularResponse>

    @FormUrlEncoded
    @POST("Backend/Merchant/API/Payout/CashFree/DirectPayout/main.php")
    fun sendPayoutOTP(
        @Field("trans_mode") trans_mode: String,
        @Field("amount") amount: String,
        @Field("sendotp") sendotp: String = "sendotp"
    ): Observable<SystemResponse<OTPResponse?>>

    //X-PAYOUT


    //FingpayBoard
    @FormUrlEncoded
    @POST("Backend/Merchant/API/Recharge/PaySprint/hlr.php")
    fun browsePlanAdmin(
        @Field("browse_plan") browse_plan: String?,
        @Field("number") number: String?,
        @Field("operatorCode") operatorCode: String?
    ): Observable<SystemResponse<Any?>>

}