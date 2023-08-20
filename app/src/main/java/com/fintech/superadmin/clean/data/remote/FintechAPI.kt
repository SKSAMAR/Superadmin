package com.fintech.superadmin.clean.data.remote

import com.fintech.superadmin.clean.data.remote.dto.cashfree.CashFree
import com.fintech.superadmin.clean.data.remote.dto.cashfree.PayoutBeneficiary
import com.fintech.superadmin.clean.data.remote.dto.otps.OTPResponse
import com.fintech.superadmin.clean.data.remote.dto.rakeshpan.RakeshUTIApplyDto
import com.fintech.superadmin.clean.data.remote.dto.refer.ReferDto
import com.fintech.superadmin.clean.data.remote.dto.reward.ScratchCardData
import com.fintech.superadmin.clean.data.remote.dto.suvidhaPayout.SuvidhaBeneficiary
import com.fintech.superadmin.data.dto.CouponDto
import com.fintech.superadmin.data.network.responses.DMTSendAmountResponse
import com.fintech.superadmin.data.network.responses.RegularResponse
import com.fintech.superadmin.data.network.responses.SystemResponse
import com.fintech.superadmin.data.network.responses.UTIPANRedirect
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    fun ekosendAmountDMT(
        @Field("bene_id") bene_id: String,
        @Field("send_amount") send_amount: String,
        @Field("send_am_acc") send_am_acc: String,
        @Field("txn_type") txn_type: String,
        @Field("ifsc") ifsc: String,
        @Field("senderMobile") senderMobile: String,
        @Field("tpin") tpin: String
    ): Observable<DMTSendAmountResponse>


    /**
    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    fun ekosendAmountOTP(
    @Field("otpSendTime") otpSendTime: String,
    @Field("send_am") send_am: String,
    @Field("sendotp") sendotp: String = "sendotp"
    ): Observable<OTPResponse>
     **/

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


    //Suvidhaa Payout

    @FormUrlEncoded
    @POST("Backend/Merchant/API/Payout/Suvidhaa/main.php")
    fun addBeneficiary(
        @Field("beneName") beneName: String?,
        @Field("accNum") accNum: String?,
        @Field("ifsc") ifsc: String?
    ): Observable<RegularResponse>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    fun getSuvidhaBeneficiary(
        @Field("search") search: String?,
        @Field("suvidhaBeneficiaries") suvidhaBeneficiaries: String = "suvidhaBeneficiaries"
    ): Observable<SystemResponse<List<SuvidhaBeneficiary>?>>

    @FormUrlEncoded
    @POST("Backend/Merchant/API/Payout/Suvidhaa/main.php")
    fun doSuvidhaaPayout(
        @Field("amount") amount: String,
        @Field("beneRowId") beneRowId: String,
        @Field("doTxn") doTxn: String = "doTxn",
    ): Observable<RegularResponse>


    //Suvidhaa Payout

    //NSDLPAN

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    fun naslCreationPanCardRedirect(
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("mode") mode: String,
        @Field("title") title: String,
        @Field("gender") gender: String,
        @Field("nsdlCreation") nsdlCreation: String = "nsdlCreation",
    ): Observable<UTIPANRedirect>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    fun naslChangePanCardRedirect(
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("email") email: String,
        @Field("mode") mode: String,
        @Field("title") title: String,
        @Field("gender") gender: String,
        @Field("nsdlChange") nsdlChange: String = "nsdlChange",
    ): Observable<UTIPANRedirect>

    //NSDL PAN


    //MAGIC WALLET

    @FormUrlEncoded
    @POST("Backend/Merchant/360_Wallet/360_withdraw.php")
    fun magicWalletWithdraw(
        @Field("amount") amount: String
    ): Observable<RegularResponse>

    //MAGIC WALLET

    //RAKESH

    @FormUrlEncoded
    @POST("Merchant/UTI_PAN_RK.php")
    fun rakeshUTIRegister(
        @Field("name") name: String,
        @Field("address") address: String,
        @Field("pincode") pincode: String,
        @Field("state") state: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("pan") pan: String,
        @Field("shop") shop: String,
        @Field("adhaar") adhaar: String,
        @Field("apply_agent") apply_agent: String = "apply_agent",
    ): Observable<RakeshUTIApplyDto>


    @FormUrlEncoded
    @POST("Merchant/NSDL_PAN_RK.php")
    fun rakeshNSDLRegister(
        @Field("name") name: String,
        @Field("address") address: String,
        @Field("pincode") pincode: String,
        @Field("state") state: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("pan") pan: String,
        @Field("shop") shop: String,
        @Field("adhaar") adhaar: String,
        @Field("apply_agent") apply_agent: String = "apply_agent",
    ): Observable<RakeshUTIApplyDto>


    @FormUrlEncoded
    @POST("Merchant/UTI_PAN_RK.php")
    fun purchaseCoupon(
        @Field("coupentype") coupentype: String,
        @Field("coupennum") coupennum: String,
        @Field("coupenpurchase") coupenpurchase: String = "coupenpurchase",
    ): Observable<RakeshUTIApplyDto>

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    fun getUTIs(
        @Field("rakeshUTICoupons") rakeshUTICoupons: String = "rakeshUTICoupons",
    ): Observable<SystemResponse<List<CouponDto>>>


    @GET("Merchant/UTI_PAN_RK.php")
    fun checkCouponStatus(
        @Query("id") id: String,
        @Query("updatestatus") updatestatus: String = "updatestatus",
    ): Observable<RakeshUTIApplyDto>


    @GET("Merchant/UTI_PAN_RK.php")
    fun resetUTIPass(
        @Query("resetPass") resetPass: String = "resetPass",
    ): Observable<RakeshUTIApplyDto>

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    fun everResetted(
        @Field("rakeshUTIResetCheck") rakeshUTIResetCheck: String = "rakeshUTIResetCheck",
    ): Observable<RegularResponse>


    @GET("Merchant/UTI_PAN_RK.php")
    fun checkResetPassword(
        @Query("CheckresetPass") CheckresetPass: String,
    ): Observable<RakeshUTIApplyDto>



    @FormUrlEncoded
    @POST("Backend/Merchant/API/PanCard/Ekyc/pan.php")
    fun nsdlChangePanCardSubmit(
        @Field("name") name: String,
        @Field("em") em: String,
        @Field("mob") mob: String,
        @Field("gen") gen: String,
        @Field("appmode") appmode: String,
    ): Observable<RegularResponse>

    //RAKESH
}
