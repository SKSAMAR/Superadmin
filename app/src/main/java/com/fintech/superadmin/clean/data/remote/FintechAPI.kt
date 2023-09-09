package com.fintech.superadmin.clean.data.remote

import com.fintech.superadmin.clean.data.remote.dto.cashfree.CashFree
import com.fintech.superadmin.clean.data.remote.dto.cashfree.PayoutBeneficiary
import com.fintech.superadmin.clean.data.remote.dto.otps.OTPResponse
import com.fintech.superadmin.clean.data.remote.dto.refer.ReferDto
import com.fintech.superadmin.clean.data.remote.dto.reward.ScratchCardData
import com.fintech.superadmin.clean.data.remote.dto.suvidhaPayout.SuvidhaBeneficiary
import com.fintech.superadmin.clean.presentation.gstregistration.toRequestBody
import com.fintech.superadmin.data.network.responses.DMTSendAmountResponse
import com.fintech.superadmin.data.network.responses.RegularResponse
import com.fintech.superadmin.data.network.responses.SystemResponse
import com.fintech.superadmin.data.network.responses.UTIPANRedirect
import io.reactivex.rxjava3.core.Observable
import okhttp3.Call
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    fun gstFormSubmit(
        @Field("CUSTOMER_NAME") CUSTOMER_NAME: String,
        @Field("CUSTOMER_NUMBER") CUSTOMER_NUMBER: String,
        @Field("COMPANY_NAME") COMPANY_NAME: String,
        @Field("CUSTOMER_EMAIL") CUSTOMER_EMAIL: String,
        @Field("SELECT_STATE") SELECT_STATE: String,
        @Field("CUSTOMER_ADDRESS") CUSTOMER_ADDRESS: String,
        @Field("NAME_OF_TAXPAYER") NAME_OF_TAXPAYER: String,
        @Field("GSTIN_OF_THE_TAXPAYER") GSTIN_OF_THE_TAXPAYER: String,
        @Field("NAME_OF_CONTACT_PERSON") NAME_OF_CONTACT_PERSON: String,
        @Field("TAX_PAYER_MOBILE_NUMBER") TAX_PAYER_MOBILE_NUMBER: String,
        @Field("TAX_PAYER_EMAIL_ID") TAX_PAYER_EMAIL_ID: String,
        @Field("TAXPAYER_TYPE") TAXPAYER_TYPE: String,
        @Field("TAX_FILLING_PERIOD") TAX_FILLING_PERIOD: String,
        @Field("SELECT_GSTR") SELECT_GSTR: String,
        @Field("TURNOVER") TURNOVER: String,
        @Field("GSTR_USER_ID") GSTR_USER_ID: String,
        @Field("GSTR_PORTAL_PASSWORD") GSTR_PORTAL_PASSWORD: String,
        @Field("SALES_TAX_VALUE") SALES_TAX_VALUE: String,
        @Field("SELECT_IGST_PERCENTAGE") SELECT_IGST_PERCENTAGE: String,
        @Field("SELECT_CGST_PERCENTAGE") SELECT_CGST_PERCENTAGE: String,
        @Field("SELECT_SGST_PERCENTAGE") SELECT_SGST_PERCENTAGE: String,
        @Field("NILRATED_OR_EXEMPTED_SUPPLY") NILRATED_OR_EXEMPTED_SUPPLY: String,
        @Field("UPLOAD_DOCUMENT_1") UPLOAD_DOCUMENT_1: String,
        @Field("UPLOAD_DOCUMENT_2") UPLOAD_DOCUMENT_2: String,
        @Field("UPLOAD_DOCUMENT_3") UPLOAD_DOCUMENT_3: String,
        @Field("UPLOAD_DOCUMENT_4") UPLOAD_DOCUMENT_4: String
    ): Observable<RegularResponse>


    @Multipart
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    fun sendRegistrationRequest(
        @Part("c_name") cName: RequestBody,
        @Part("c_num") cNum: RequestBody,
        @Part("cmnpy_name") cmnpyName: RequestBody,
        @Part("cus_email") cusEmail: RequestBody,
        @Part("state") state: RequestBody,
        @Part("cus_address") cusAddress: RequestBody,
        @Part("pan_name") panName: RequestBody,
        @Part("pan_no") panNo: RequestBody,
        @Part("contact_p") contactP: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("email") email: RequestBody,
        @Part("bussiness_type") bussinessType: RequestBody,
        @Part("consitituion_of_buss") consitituionOfBuss: RequestBody,
        @Part("option_of_composition") optionOfComposition: RequestBody,
        @Part("commenceement_b") commenceementB: RequestBody,
        @Part("d_fname") dFname: RequestBody,
        @Part("d_mname") dMname: RequestBody,
        @Part("d_lname") dLname: RequestBody,
        @Part("d_gender") dGender: RequestBody,
        @Part("d_dob") dDob: RequestBody,
        @Part("d_contact_no") dContactNo: RequestBody,
        @Part("d_email") dEmail: RequestBody,
        @Part("d_aadhar_no") dAadharNo: RequestBody,
        @Part("d_address") dAddress: RequestBody,
        @Part("d_building_no") dBuildingNo: RequestBody,
        @Part("d_postoffice") dPostOffice: RequestBody,
        @Part("d_locality") dLocality: RequestBody,
        @Part("d_state") dState: RequestBody,
        @Part("d_city") dCity: RequestBody,
        @Part dProfile1: MultipartBody.Part?,
        @Part dProfile2: MultipartBody.Part?,
        @Part dProfile3: MultipartBody.Part?,
        @Part dProfile4: MultipartBody.Part?,
        @Part dProfile5: MultipartBody.Part?,
        @Part dProfile6: MultipartBody.Part?,
        @Part("d3_fname") d3Fname: RequestBody,
        @Part("d3_mname") d3Mname: RequestBody,
        @Part("d3_lname") d3Lname: RequestBody,
        @Part("d3_gender") d3Gender: RequestBody,
        @Part("d3_dob") d3Dob: RequestBody,
        @Part("d3_contact_no") d3ContactNo: RequestBody,
        @Part("d3_email") d3Email: RequestBody,
        @Part("d3_aadhar") d3Aadhar: RequestBody,
        @Part("d3_block") d3Block: RequestBody,
        @Part("d3_building") d3Building: RequestBody,
        @Part("d3_postoffice") d3PostOffice: RequestBody,
        @Part("d3_locality") d3Locality: RequestBody,
        @Part("d3_state") d3State: RequestBody,
        @Part("d3_city") d3City: RequestBody,
        @Part dProfile7: MultipartBody.Part?,
        @Part dProfile8: MultipartBody.Part?,
        @Part dProfile9: MultipartBody.Part?,
        @Part dProfile10: MultipartBody.Part?,
        @Part("d2_fname") d2Fname: RequestBody,
        @Part("d2_mname") d2Mname: RequestBody,
        @Part("d2_lname") d2Lname: RequestBody,
        @Part("d2_gender") d2Gender: RequestBody,
        @Part("d2_dob") d2Dob: RequestBody,
        @Part("d2_contact_no") d2ContactNo: RequestBody,
        @Part("d2_email") d2Email: RequestBody,
        @Part("d2_aadhar") d2Aadhar: RequestBody,
        @Part("d2_block") d2Block: RequestBody,
        @Part("d2_building") d2Building: RequestBody,
        @Part("d2_postoffice") d2PostOffice: RequestBody,
        @Part("d2_locality") d2Locality: RequestBody,
        @Part("d2_state") d2State: RequestBody,
        @Part("d2_city") d2City: RequestBody,
        @Part dProfile11: MultipartBody.Part?,
        @Part dProfile12: MultipartBody.Part?,
        @Part dProfile13: MultipartBody.Part?,
        @Part dProfile14: MultipartBody.Part?,
        @Part("d4_fname") d4Fname: RequestBody,
        @Part("d4_mname") d4Mname: RequestBody,
        @Part("d4_lname") d4Lname: RequestBody,
        @Part("d4_gender") d4Gender: RequestBody,
        @Part("d4_dob") d4Dob: RequestBody,
        @Part("d4_contact_no") d4ContactNo: RequestBody,
        @Part("d4_email") d4Email: RequestBody,
        @Part("d4_aadhar") d4Aadhar: RequestBody,
        @Part("d4_block") d4Block: RequestBody,
        @Part("d4_building") d4Building: RequestBody,
        @Part("d4_postoffice") d4PostOffice: RequestBody,
        @Part("d4_locality") d4Locality: RequestBody,
        @Part("d4_state") d4State: RequestBody,
        @Part("d4_city") d4City: RequestBody,
        @Part dProfile15: MultipartBody.Part?,
        @Part dProfile16: MultipartBody.Part?,
        @Part dProfile17: MultipartBody.Part?,
        @Part dProfile18: MultipartBody.Part?,
        @Part("d5_fname") d5Fname: RequestBody,
        @Part("d5_mname") d5Mname: RequestBody,
        @Part("d5_lname") d5Lname: RequestBody,
        @Part("d5_gender") d5Gender: RequestBody,
        @Part("d5_dob") d5Dob: RequestBody,
        @Part("d5_contact_no") d5ContactNo: RequestBody,
        @Part("d5_email") d5Email: RequestBody,
        @Part("d5_aadhar") d5Aadhar: RequestBody,
        @Part("d5_block") d5Block: RequestBody,
        @Part("d5_building") d5Building: RequestBody,
        @Part("d5_postoffice") d5PostOffice: RequestBody,
        @Part("d5_locality") d5Locality: RequestBody,
        @Part("d5_state") d5State: RequestBody,
        @Part("d5_city") d5City: RequestBody,
        @Part dProfile19: MultipartBody.Part?,
        @Part dProfile20: MultipartBody.Part?,
        @Part dProfile21: MultipartBody.Part?,
        @Part dProfile22: MultipartBody.Part?,
        @Part("d6_fname") d6Fname: RequestBody,
        @Part("d6_mname") d6Mname: RequestBody,
        @Part("d6_lname") d6Lname: RequestBody,
        @Part("d6_gender") d6Gender: RequestBody,
        @Part("d6_dob") d6Dob: RequestBody,
        @Part("d6_contact_no") d6ContactNo: RequestBody,
        @Part("d6_email") d6Email: RequestBody,
        @Part("d6_aadhar") d6Aadhar: RequestBody,
        @Part("d6_block") d6Block: RequestBody,
        @Part("d6_building") d6Building: RequestBody,
        @Part("d6_postoffice") d6PostOffice: RequestBody,
        @Part("d6_locality") d6Locality: RequestBody,
        @Part("d6_state") d6State: RequestBody,
        @Part("d6_city") d6City: RequestBody,
        @Part dProfile23: MultipartBody.Part?,
        @Part dProfile24: MultipartBody.Part?,
        @Part dProfile25: MultipartBody.Part?,
        @Part dProfile26: MultipartBody.Part?,
        @Part("ppb_address") ppbAddress: RequestBody,
        @Part("ppb_building") ppbBuilding: RequestBody,
        @Part("ppb_postoffice") ppbPostOffice: RequestBody,
        @Part("ppb_locality") ppbLocality: RequestBody,
        @Part("ppb_state") ppbState: RequestBody,
        @Part("bank_account") bankAccount: RequestBody,
        @Part("ifsc_code") ifscCode: RequestBody,
        @Part("acc_type") accType: RequestBody,
        @Part dDocs1: MultipartBody.Part?,
        @Part dDocs2: MultipartBody.Part?,
        @Part dDocs3: MultipartBody.Part?,
        @Part dDocs4: MultipartBody.Part?,
        @Part("product1") product1: RequestBody,
        @Part("product2") product2: RequestBody,
        @Part("product3") product3: RequestBody,
        @Part("product4") product4: RequestBody,
        @Part("product5") product5: RequestBody,
        @Part("prossession") prossession: RequestBody,
        @Part("buss_activity") bussActivity: RequestBody,
        @Part("authorised_sign") authorisedSign: RequestBody,
        @Part("place") place: RequestBody,
        @Part("ticket_id") ticketId: RequestBody,
        @Part("main_owner") mainOwner: RequestBody,
        @Part("main_owner_id") mainOwnerId: RequestBody
    ): Observable<RegularResponse>


    @Multipart
    @POST("Backend/Merchant/GST/GSTRegistration.php")
    fun submitFormDataGst(
        @Part("c_name") cName: RequestBody,
        @Part("c_num") cNum: RequestBody,
        @Part("cmnpy_name") cmnpyName: RequestBody,
        @Part("cus_email") cusEmail: RequestBody,
        @Part("state") state: RequestBody,
        @Part("cus_address") cusAddress: RequestBody,
        @Part("pan_name") panName: RequestBody,
        @Part("pan_no") panNo: RequestBody,
        @Part("contact_p") contactP: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("email") email: RequestBody,
        @Part("bussiness_type") bussinessType: RequestBody,
        @Part("consitituion_of_buss") consitituionOfBuss: RequestBody,
        @Part("option_of_composition") optionOfComposition: RequestBody,
        @Part("commenceement_b") commenceementB: RequestBody,
        @Part("d_fname") dFname: RequestBody,
        @Part("d_mname") dMname: RequestBody,
        @Part("d_lname") dLname: RequestBody,
        @Part("d_gender") dGender: RequestBody,
        @Part("d_dob") dDob: RequestBody,
        @Part("d_contact_no") dContactNo: RequestBody,
        @Part("d_email") dEmail: RequestBody,
        @Part("d_aadhar_no") dAadharNo: RequestBody,
        @Part("d_address") dAddress: RequestBody,
        @Part("d_building_no") dBuildingNo: RequestBody,
        @Part("d_postoffice") dPostoffice: RequestBody,
        @Part("d_locality") dLocality: RequestBody,
        @Part("d_state") dState: RequestBody,
        @Part("d_city") dCity: RequestBody,
        @Part("d3_fname") d3Fname: RequestBody,
        @Part("d3_mname") d3Mname: RequestBody,
        @Part("d3_lname") d3Lname: RequestBody,
        @Part("d3_gender") d3Gender: RequestBody,
        @Part("d3_dob") d3Dob: RequestBody,
        @Part("d3_contact_no") d3ContactNo: RequestBody,
        @Part("d3_email") d3Email: RequestBody,
        @Part("d3_aadhar") d3Aadhar: RequestBody,
        @Part("d3_block") d3Block: RequestBody,
        @Part("d3_building") d3Building: RequestBody,
        @Part("d3_postoffice") d3Postoffice: RequestBody,
        @Part("d3_locality") d3Locality: RequestBody,
        @Part("d3_state") d3State: RequestBody,
        @Part("d3_city") d3City: RequestBody,
        @Part("d2_fname") d2Fname: RequestBody,
        @Part("d2_mname") d2Mname: RequestBody,
        @Part("d2_lname") d2Lname: RequestBody,
        @Part("d2_gender") d2Gender: RequestBody,
        @Part("d2_dob") d2Dob: RequestBody,
        @Part("d2_contact_no") d2ContactNo: RequestBody,
        @Part("d2_email") d2Email: RequestBody,
        @Part("d2_aadhar_no") d2AadharNo: RequestBody,
        @Part("d2_address") d2Address: RequestBody,
        @Part("d2_building_no") d2BuildingNo: RequestBody,
        @Part("d2_postoffice") d2Postoffice: RequestBody,
        @Part("d2_locality") d2Locality: RequestBody,
        @Part("d2_state") d2State: RequestBody,
        @Part("d2_city") d2City: RequestBody,
        @Part("d4_fname") d4Fname: RequestBody,
        @Part("d4_mname") d4Mname: RequestBody,
        @Part("d4_lname") d4Lname: RequestBody,
        @Part("d4_gender") d4Gender: RequestBody,
        @Part("d4_dob") d4Dob: RequestBody,
        @Part("d4_contact_no") d4ContactNo: RequestBody,
        @Part("d4_email") d4Email: RequestBody,
        @Part("d4_aadhar_no") d4AadharNo: RequestBody,
        @Part("d4_address") d4Address: RequestBody,
        @Part("d4_building_no") d4BuildingNo: RequestBody,
        @Part("d4_postoffice") d4Postoffice: RequestBody,
        @Part("d4_locality") d4Locality: RequestBody,
        @Part("d4_state") d4State: RequestBody,
        @Part("d4_city") d4City: RequestBody,
        @Part("d5_fname") d5Fname: RequestBody,
        @Part("d5_mname") d5Mname: RequestBody,
        @Part("d5_lname") d5Lname: RequestBody,
        @Part("d5_gender") d5Gender: RequestBody,
        @Part("d5_dob") d5Dob: RequestBody,
        @Part("d5_contact_no") d5ContactNo: RequestBody,
        @Part("d5_email") d5Email: RequestBody,
        @Part("d5_aadhar_no") d5AadharNo: RequestBody,
        @Part("d5_address") d5Address: RequestBody,
        @Part("d5_building_no") d5BuildingNo: RequestBody,
        @Part("d5_postoffice") d5Postoffice: RequestBody,
        @Part("d5_locality") d5Locality: RequestBody,
        @Part("d5_state") d5State: RequestBody,
        @Part("d5_city") d5City: RequestBody,
        @Part("d6_fname") d6Fname: RequestBody,
        @Part("d6_mname") d6Mname: RequestBody,
        @Part("d6_lname") d6Lname: RequestBody,
        @Part("d6_gender") d6Gender: RequestBody,
        @Part("d6_dob") d6Dob: RequestBody,
        @Part("d6_contact_no") d6ContactNo: RequestBody,
        @Part("d6_email") d6Email: RequestBody,
        @Part("d6_aadhar_no") d6AadharNo: RequestBody,
        @Part("d6_address") d6Address: RequestBody,
        @Part("d6_building_no") d6BuildingNo: RequestBody,
        @Part("d6_postoffice") d6Postoffice: RequestBody,
        @Part("d6_locality") d6Locality: RequestBody,
        @Part("d6_state") d6State: RequestBody,
        @Part("d6_city") d6City: RequestBody,
        @Part("ppb_address") ppbAddress: RequestBody,
        @Part("ppb_building") ppbBuilding: RequestBody,
        @Part("ppb_postoffice") ppbPostoffice: RequestBody,
        @Part("ppb_locality") ppbLocality: RequestBody,
        @Part("ppb_state") ppbState: RequestBody,
        @Part("bank_account") bankAccount: RequestBody,
        @Part("ifsc_code") ifscCode: RequestBody,
        @Part("acc_type") accType: RequestBody,
        @Part("product1") product1: RequestBody,
        @Part("product2") product2: RequestBody,
        @Part("product3") product3: RequestBody,
        @Part("product4") product4: RequestBody,
        @Part("product5") product5: RequestBody,
        @Part("prossession") prossession: RequestBody,
        @Part("buss_activity") bussActivity: RequestBody,
        @Part("authorised_sign") authorisedSign: RequestBody,
        @Part("place") place: RequestBody,
        @Part dProfile: MultipartBody.Part?,
        @Part d3Profile: MultipartBody.Part?,
        @Part d2Profile: MultipartBody.Part?,
        @Part d4Profile: MultipartBody.Part?,
        @Part d5Profile: MultipartBody.Part?,
        @Part d6Profile: MultipartBody.Part?,
        @Part image: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?
    ): Observable<RegularResponse>


    @Multipart
    @POST("Backend/Merchant/GST/ITRFillingSalaried.php") // Replace with your actual endpoint URL
    fun submitITRFillingSalaraied(
        @Part("c_name") c_name: RequestBody,
        @Part("c_num") c_num: RequestBody,
        @Part("cmnpy_name") cmnpy_name: RequestBody,
        @Part("cus_email") cus_email: RequestBody,
        @Part("state") state: RequestBody,
        @Part("cus_address") cus_address: RequestBody,
        @Part("applicant_name") applicant_name: RequestBody,
        @Part("father_name") father_name: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("pan_no") pan_no: RequestBody,
        @Part("aadhar_no") aadhar_no: RequestBody,
        @Part("applicant_address") applicant_address: RequestBody,
        @Part("pin_code") pin_code: RequestBody,
        @Part("applicant_email") applicant_email: RequestBody,
        @Part("applicant_mobile_no") applicant_mobile_no: RequestBody,
        @Part("finacial_year") finacial_year: RequestBody,
        @Part("acc_no") acc_no: RequestBody,
        @Part("ifsc_code") ifsc_code: RequestBody,
        @Part("gross_salary") gross_salary: RequestBody,
        @Part("decduction") decduction: RequestBody,
        @Part("net_salary") net_salary: RequestBody,
        @Part("turn_over") turn_over: RequestBody,
        @Part("income") income: RequestBody,
        @Part("house_prop") house_prop: RequestBody,
        @Part("bank_account") bank_account: RequestBody,
        @Part("tution") tution: RequestBody,
        @Part("health_insurance") health_insurance: RequestBody,
        @Part("other_deduction") other_deduction: RequestBody,
        @Part("deduction_amount") deduction_amount: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("ticket_rise") ticket_rise: RequestBody = "1".toRequestBody(),
    ): Observable<RegularResponse>


    @Multipart
    @POST("Backend/Merchant/GST/ITRFillingBusiness.php")
    fun submitITRFilingBusiness(
        @Part("c_name") c_name: RequestBody,
        @Part("c_num") c_num: RequestBody,
        @Part("cmnpy_name") cmnpy_name: RequestBody,
        @Part("cus_email") cus_email: RequestBody,
        @Part("state") state: RequestBody,
        @Part("cus_address") cus_address: RequestBody,
        @Part("applicant_name") applicant_name: RequestBody,
        @Part("father_name") father_name: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("pan_no") pan_no: RequestBody,
        @Part("aadhar_no") aadhar_no: RequestBody,
        @Part("applicant_address") applicant_address: RequestBody,
        @Part("pin_code") pin_code: RequestBody,
        @Part("applicant_email") applicant_email: RequestBody,
        @Part("applicant_mobile_no") applicant_mobile_no: RequestBody,
        @Part("finacial_year") finacial_year: RequestBody,
        @Part("acc_no") acc_no: RequestBody,
        @Part("ifsc_code") ifsc_code: RequestBody,
        @Part("turn_over") turn_over: RequestBody,
        @Part("income") income: RequestBody,
        @Part("house_prop") house_prop: RequestBody,
        @Part("bank_account") bank_account: RequestBody,
        @Part("tution") tution: RequestBody,
        @Part("salary_type") salary_type: RequestBody,
        @Part docs: MultipartBody.Part?,
        @Part("ticket_rise") ticket_rise: RequestBody = "1".toRequestBody(),
    ): Observable<RegularResponse>


    @Multipart
    @POST("Backend/Merchant/GST/CompanyIncorporation.php")
    fun uploadFormCompanyIcoOp(
        @Part("c_name") c_name: RequestBody,
        @Part("c_num") c_num: RequestBody,
        @Part("cmnpy_name") cmnpy_name: RequestBody,
        @Part("cus_email") cus_email: RequestBody,
        @Part("state") x_state: RequestBody,
        @Part("cus_address") cus_address: RequestBody,
        @Part("prefernce1") prefernce1: RequestBody,
        @Part("prefernce2") prefernce2: RequestBody,
        @Part("activity") activity: RequestBody,
        @Part("d_fname") d_fname: RequestBody,
        @Part("d_mname") d_mname: RequestBody,
        @Part("d_lname") d_lname: RequestBody,
        @Part("d_gender") d_gender: RequestBody,
        @Part("d_dob") d_dob: RequestBody,
        @Part("d_contact_no") d_contact_no: RequestBody,
        @Part("d_email") d_email: RequestBody,
        @Part("d_father_name") d_father_name: RequestBody,
        @Part("d_father_mname") d_father_mname: RequestBody,
        @Part("d_father_lname") d_father_lname: RequestBody,
        @Part d_passportphoto: MultipartBody.Part?,
        @Part d_aadhar_pic: MultipartBody.Part?,
        @Part d_pancard: MultipartBody.Part?,
        @Part("d2_fname") d2_fname: RequestBody,
        @Part("d2_mname") d2_mname: RequestBody,
        @Part("d2_lname") d2_lname: RequestBody,
        @Part("d2_gender") d2_gender: RequestBody,
        @Part("d2_dob") d2_dob: RequestBody,
        @Part("d2_contact_no") d2_contact_no: RequestBody,
        @Part("d2_email") d2_email: RequestBody,
        @Part("d2_father_name") d2_father_name: RequestBody,
        @Part("d2_father_mname") d2_father_mname: RequestBody,
        @Part("d2_father_lname") d2_father_lname: RequestBody,
        @Part d2_passportphoto: MultipartBody.Part?,
        @Part d2_aadhar_pic: MultipartBody.Part?,
        @Part d2_pancard: MultipartBody.Part?,
        @Part("d3_fname") d3_fname: RequestBody,
        @Part("d3_mname") d3_mname: RequestBody,
        @Part("d3_lname") d3_lname: RequestBody,
        @Part("d3_gender") d3_gender: RequestBody,
        @Part("d3_dob") d3_dob: RequestBody,
        @Part("d3_contact_no") d3_contact_no: RequestBody,
        @Part("d3_email") d3_email: RequestBody,
        @Part("d3_father_name") d3_father_name: RequestBody,
        @Part("d3_father_mname") d3_father_mname: RequestBody,
        @Part("d3_father_lname") d3_father_lname: RequestBody,
        @Part d3_passportphoto: MultipartBody.Part?,
        @Part d3_aadhar_pic: MultipartBody.Part?,
        @Part d3_pancard: MultipartBody.Part?,
        @Part("d4_fname") d4_fname: RequestBody,
        @Part("d4_mname") d4_mname: RequestBody,
        @Part("d4_lname") d4_lname: RequestBody,
        @Part("d4_gender") d4_gender: RequestBody,
        @Part("d4_dob") d4_dob: RequestBody,
        @Part("d4_contact_no") d4_contact_no: RequestBody,
        @Part("d4_email") d4_email: RequestBody,
        @Part("d4_father_name") d4_father_name: RequestBody,
        @Part("d4_father_mname") d4_father_mname: RequestBody,
        @Part("d4_father_lname") d4_father_lname: RequestBody,
        @Part d4_passportphoto: MultipartBody.Part?,
        @Part d4_aadhar_pic: MultipartBody.Part?,
        @Part d4_pancard: MultipartBody.Part?,
        @Part("d5_fname") d5_fname: RequestBody,
        @Part("d5_mname") d5_mname: RequestBody,
        @Part("d5_lname") d5_lname: RequestBody,
        @Part("d5_gender") d5_gender: RequestBody,
        @Part("d5_dob") d5_dob: RequestBody,
        @Part("d5_contact_no") d5_contact_no: RequestBody,
        @Part("d5_email") d5_email: RequestBody,
        @Part("d5_father_name") d5_father_name: RequestBody,
        @Part("d5_father_mname") d5_father_mname: RequestBody,
        @Part("d5_father_lname") d5_father_lname: RequestBody,
        @Part d5_passportphoto: MultipartBody.Part?,
        @Part d5_aadhar_pic: MultipartBody.Part?,
        @Part d5_pancard: MultipartBody.Part?,
        @Part("d6_fname") d6_fname: RequestBody,
        @Part("d6_mname") d6_mname: RequestBody,
        @Part("d6_lname") d6_lname: RequestBody,
        @Part("d6_gender") d6_gender: RequestBody,
        @Part("d6_dob") d6_dob: RequestBody,
        @Part("d6_contact_no") d6_contact_no: RequestBody,
        @Part("d6_email") d6_email: RequestBody,
        @Part("d6_father_name") d6_father_name: RequestBody,
        @Part("d6_father_mname") d6_father_mname: RequestBody,
        @Part("d6_father_lname") d6_father_lname: RequestBody,
        @Part d6_passportphoto: MultipartBody.Part?,
        @Part d6_aadhar_pic: MultipartBody.Part?,
        @Part d6_pancard: MultipartBody.Part?,
        @Part("copmany_registration") copmany_registration: RequestBody,
        @Part("company_type") company_type: RequestBody,
        @Part("ticket_rise") ticket_rise: RequestBody = "1".toRequestBody(),
    ): Observable<RegularResponse>


    @Multipart
    @POST("Backend/Merchant/GST/PartnershipFirm.php")
    fun uploadFormPartnershipFirm(
        // Parameters for text fields
        @Part("c_name") c_name: RequestBody, // Company Name
        @Part("c_num") c_num: RequestBody, // Customer Number
        @Part("cmnpy_name") cmnpy_name: RequestBody, // Company Name
        @Part("cus_email") cus_email: RequestBody, // Customer Email
        @Part("state") x_state: RequestBody, // State
        @Part("cus_address") cus_address: RequestBody, // Customer Address
        @Part("name_firm") name_firm: RequestBody, // Name of the Firm
        @Part("register_address") register_address: RequestBody, // Registered Address
        @Part("date_registration") date_registration: RequestBody, // Date of Registration
        @Part("ratio") ratio: RequestBody, // Ratio
        @Part("object_firm") object_firm: RequestBody, // Object of the Firm

        // Partner 1
        @Part("p_fname") p_fname: RequestBody, // Partner 1 First Name
        @Part("p_fathername") p_fathername: RequestBody, // Partner 1 Father's Name
        @Part("p_dob") p_dob: RequestBody, // Partner 1 Date of Birth
        @Part("p_residence_address") p_residence_address: RequestBody, // Partner 1 Residence Address

        // Passport photo 1 upload
        @Part p_photo: MultipartBody.Part?, // Passport photo for Partner 1
        @Part p_aadhar_pic: MultipartBody.Part?, // Aadhar card photo for Partner 1
        @Part p_sign: MultipartBody.Part?, // Signature photo for Partner 1

        // Partner 2
        @Part("p2_fname") p2_fname: RequestBody, // Partner 2 First Name
        @Part("p2_fathername") p2_fathername: RequestBody, // Partner 2 Father's Name
        @Part("p2_dob") p2_dob: RequestBody, // Partner 2 Date of Birth
        @Part("p2_residence_address") p2_residence_address: RequestBody, // Partner 2 Residence Address

        // Passport photo 2 upload
        @Part p_photo2: MultipartBody.Part?, // Passport photo for Partner 2
        @Part p_aadhar_pic2: MultipartBody.Part?, // Aadhar card photo for Partner 2
        @Part p2_sign: MultipartBody.Part?, // Signature photo for Partner 2


        // Partner 3
        @Part("p3_fname") p3_fname: RequestBody, // Partner 3 First Name
        @Part("p3_fathername") p3_fathername: RequestBody, // Partner 3 Father's Name
        @Part("p3_dob") p3_dob: RequestBody, // Partner 3 Date of Birth
        @Part("p3_residence_address") p3_residence_address: RequestBody, // Partner 3 Residence Address

// Passport photo 3 upload
        @Part p_photo3: MultipartBody.Part?, // Passport photo for Partner 3
        @Part p_aadhar_pic3: MultipartBody.Part?, // Aadhar card photo for Partner 3
        @Part p3_sign: MultipartBody.Part?, // Signature photo for Partner 3

// Partner 4
        @Part("p4_fname") p4_fname: RequestBody, // Partner 4 First Name
        @Part("p4_fathername") p4_fathername: RequestBody, // Partner 4 Father's Name
        @Part("p4_dob") p4_dob: RequestBody, // Partner 4 Date of Birth
        @Part("p4_residence_address") p4_residence_address: RequestBody, // Partner 4 Residence Address

// Passport photo 4 upload
        @Part p_photo4: MultipartBody.Part?, // Passport photo for Partner 4
        @Part p_aadhar_pic4: MultipartBody.Part?, // Aadhar card photo for Partner 4
        @Part p4_sign: MultipartBody.Part?, // Signature photo for Partner 4

// Partner 5
        @Part("p5_fname") p5_fname: RequestBody, // Partner 5 First Name
        @Part("p5_fathername") p5_fathername: RequestBody, // Partner 5 Father's Name
        @Part("p5_dob") p5_dob: RequestBody, // Partner 5 Date of Birth
        @Part("p5_residence_address") p5_residence_address: RequestBody, // Partner 5 Residence Address

// Passport photo 5 upload
        @Part p_photo5: MultipartBody.Part?, // Passport photo for Partner 5
        @Part p_aadhar_pic5: MultipartBody.Part?, // Aadhar card photo for Partner 5
        @Part p5_sign: MultipartBody.Part?, // Signature photo for Partner 5

// Partner 6
        @Part("p6_fname") p6_fname: RequestBody, // Partner 6 First Name
        @Part("p6_fathername") p6_fathername: RequestBody, // Partner 6 Father's Name
        @Part("p6_dob") p6_dob: RequestBody, // Partner 6 Date of Birth
        @Part("p6_residence_address") p6_residence_address: RequestBody, // Partner 6 Residence Address

// Passport photo 6 upload
        @Part p_photo6: MultipartBody.Part?, // Passport photo for Partner 6
        @Part p_aadhar_pic6: MultipartBody.Part?, // Aadhar card photo for Partner 6
        @Part p6_sign: MultipartBody.Part?, // Signature photo for Partner 6,
        @Part("ticket_rise") ticket_rise: RequestBody = "1".toRequestBody(),
    ): Observable<RegularResponse>


    @Multipart
    @POST("Backend/Merchant/GST/MSMERegistration.php")
    fun uploadFormMsme(
        @Part("c_name") c_name: RequestBody,
        @Part("c_num") c_num: RequestBody,
        @Part("cmnpy_name") cmnpy_name: RequestBody,
        @Part("cus_email") cus_email: RequestBody,
        @Part("cus_address") cus_address: RequestBody,
        @Part("aadhar_no") aadhar_no: RequestBody,
        @Part("entrepreneur") entrepreneur: RequestBody,
        @Part("category") category: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("handicaped") handicaped: RequestBody,
        @Part("enterpr_buss") enterpr_buss: RequestBody,
        @Part("organisation") organisation: RequestBody,
        @Part("pan_no") pan_no: RequestBody,
        @Part("block") block: RequestBody,
        @Part("building") building: RequestBody,
        @Part("postoffice") postoffice: RequestBody,
        @Part("state") x_state: RequestBody,
        @Part("city") city: RequestBody,
        @Part("o_block") o_block: RequestBody,
        @Part("o_building") o_building: RequestBody,
        @Part("o_postoffice") o_postoffice: RequestBody,
        @Part("o_state") o_state: RequestBody,
        @Part("o_city") o_city: RequestBody,
        @Part("o_mobile") o_mobile: RequestBody,
        @Part("o_email") o_email: RequestBody,
        @Part("account_no") account_no: RequestBody,
        @Part("ifsc_code") ifsc_code: RequestBody,
        @Part("oth_buss_type") oth_buss_type: RequestBody,
        @Part("commoditiessupplied") commoditiessupplied: RequestBody,
        @Part("d_service") d_service: RequestBody,
        @Part("investment_b") investment_b: RequestBody,
        @Part proprietor: MultipartBody.Part?,
        @Part("ticket_rise") ticket_rise: RequestBody = "1".toRequestBody(),
    ): Observable<RegularResponse>


    @Multipart
    @POST("Backend/Merchant/GST/GSTFilling.php")
    fun uploadFormGstFiling(
        @Part("c_name") c_name: RequestBody,
        @Part("c_num") c_num: RequestBody,
        @Part("cmnpy_name") cmnpy_name: RequestBody,
        @Part("cus_email") cus_email: RequestBody,
        @Part("cus_address") cus_address: RequestBody,
        @Part("trade_name") trade_name: RequestBody,
        @Part("state") x_state: RequestBody,
        @Part("gstin") gstin: RequestBody,
        @Part("contact_p") contact_p: RequestBody,
        @Part("tax_mb_no") tax_mb_no: RequestBody,
        @Part("tax_emailId") tax_emailId: RequestBody,
        @Part("tax_type") tax_type: RequestBody,
        @Part("tax_filling_period") tax_filling_period: RequestBody,
        @Part("gstr") gstr: RequestBody,
        @Part("turn_over") turn_over: RequestBody,
        @Part("gst_user_id") gst_user_id: RequestBody,
        @Part("gst_portal_pass") gst_portal_pass: RequestBody,
        @Part("sales_tax_val") sales_tax_val: RequestBody,
        @Part("igst_percent") igst_percent: RequestBody,
        @Part("cgst_percent") cgst_percent: RequestBody,
        @Part("sgst_percent") sgst_percent: RequestBody,
        @Part("exempted_supply") exempted_supply: RequestBody,
        @Part docs1: MultipartBody.Part?,
        @Part docs2: MultipartBody.Part?,
        @Part docs3: MultipartBody.Part?,
        @Part docs4: MultipartBody.Part?,
        @Part("ticket_rise") ticket_rise: RequestBody = "1".toRequestBody(),
    ): Observable<RegularResponse>


    @Multipart
    @POST("Backend/Merchant/GST/GSTRegistration.php")
    fun submitFormGstRegistration(
        @Part("c_name") c_name: RequestBody,
        @Part("c_num") c_num: RequestBody,
        @Part("cmnpy_name") cmnpy_name: RequestBody,
        @Part("cus_email") cus_email: RequestBody,
        @Part("x_state") x_state: RequestBody,
        @Part("cus_address") cus_address: RequestBody,
        @Part("pan_name") pan_name: RequestBody,
        @Part("pan_no") pan_no: RequestBody,
        @Part("contact_p") contact_p: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("email") email: RequestBody,
        @Part("bussiness_type") bussiness_type: RequestBody,
        @Part("consitituion_of_buss") consitituion_of_buss: RequestBody,
        @Part("option_of_composition") option_of_composition: RequestBody,
        @Part("commenceement_b") commenceement_b: RequestBody,

        // Director 1
        @Part("d_fname") d_fname: RequestBody,
        @Part("d_mname") d_mname: RequestBody,
        @Part("d_lname") d_lname: RequestBody,
        @Part("d_gender") d_gender: RequestBody,
        @Part("d_dob") d_dob: RequestBody,
        @Part("d_contact_no") d_contact_no: RequestBody,
        @Part("d_email") d_email: RequestBody,
        @Part("d_aadhar_no") d_aadhar_no: RequestBody,
        @Part("d_address") d_address: RequestBody,
        @Part("d_building_no") d_building_no: RequestBody,
        @Part("d_postoffice") d_postoffice: RequestBody,
        @Part("d_locality") d_locality: RequestBody,
        @Part("d_state") d_state: RequestBody,
        @Part("d_city") d_city: RequestBody,
        @Part d_profile: MultipartBody.Part?,

        // Director 2
        @Part("d2_fname") d2_fname: RequestBody,
        @Part("d2_mname") d2_mname: RequestBody,
        @Part("d2_lname") d2_lname: RequestBody,
        @Part("d2_gender") d2_gender: RequestBody,
        @Part("d2_dob") d2_dob: RequestBody,
        @Part("d2_contact_no") d2_contact_no: RequestBody,
        @Part("d2_email") d2_email: RequestBody,
        @Part("d2_aadhar_no") d2_aadhar_no: RequestBody,
        @Part("d2_address") d2_address: RequestBody,
        @Part("d2_building_no") d2_building_no: RequestBody,
        @Part("d2_postoffice") d2_postoffice: RequestBody,
        @Part("d2_locality") d2_locality: RequestBody,
        @Part("d2_state") d2_state: RequestBody,
        @Part("d2_city") d2_city: RequestBody,
        @Part d2_profile: MultipartBody.Part?,

        // Director 3
        @Part("d3_fname") d3_fname: RequestBody,
        @Part("d3_mname") d3_mname: RequestBody,
        @Part("d3_lname") d3_lname: RequestBody,
        @Part("d3_gender") d3_gender: RequestBody,
        @Part("d3_dob") d3_dob: RequestBody,
        @Part("d3_contact_no") d3_contact_no: RequestBody,
        @Part("d3_email") d3_email: RequestBody,
        @Part("d3_aadhar_no") d3_aadhar_no: RequestBody,
        @Part("d3_address") d3_address: RequestBody,
        @Part("d3_building_no") d3_building_no: RequestBody,
        @Part("d3_postoffice") d3_postoffice: RequestBody,
        @Part("d3_locality") d3_locality: RequestBody,
        @Part("d3_state") d3_state: RequestBody,
        @Part("d3_city") d3_city: RequestBody,
        @Part d3_profile: MultipartBody.Part?,

        // Director 4
        @Part("d4_fname") d4_fname: RequestBody,
        @Part("d4_mname") d4_mname: RequestBody,
        @Part("d4_lname") d4_lname: RequestBody,
        @Part("d4_gender") d4_gender: RequestBody,
        @Part("d4_dob") d4_dob: RequestBody,
        @Part("d4_contact_no") d4_contact_no: RequestBody,
        @Part("d4_email") d4_email: RequestBody,
        @Part("d4_aadhar_no") d4_aadhar_no: RequestBody,
        @Part("d4_address") d4_address: RequestBody,
        @Part("d4_building_no") d4_building_no: RequestBody,
        @Part("d4_postoffice") d4_postoffice: RequestBody,
        @Part("d4_locality") d4_locality: RequestBody,
        @Part("d4_state") d4_state: RequestBody,
        @Part("d4_city") d4_city: RequestBody,
        @Part d4_profile: MultipartBody.Part?,

        // Director 5
        @Part("d5_fname") d5_fname: RequestBody,
        @Part("d5_mname") d5_mname: RequestBody,
        @Part("d5_lname") d5_lname: RequestBody,
        @Part("d5_gender") d5_gender: RequestBody,
        @Part("d5_dob") d5_dob: RequestBody,
        @Part("d5_contact_no") d5_contact_no: RequestBody,
        @Part("d5_email") d5_email: RequestBody,
        @Part("d5_aadhar_no") d5_aadhar_no: RequestBody,
        @Part("d5_address") d5_address: RequestBody,
        @Part("d5_building_no") d5_building_no: RequestBody,
        @Part("d5_postoffice") d5_postoffice: RequestBody,
        @Part("d5_locality") d5_locality: RequestBody,
        @Part("d5_state") d5_state: RequestBody,
        @Part("d5_city") d5_city: RequestBody,
        @Part d5_profile: MultipartBody.Part?,

        // Director 6
        @Part("d6_fname") d6_fname: RequestBody,
        @Part("d6_mname") d6_mname: RequestBody,
        @Part("d6_lname") d6_lname: RequestBody,
        @Part("d6_gender") d6_gender: RequestBody,
        @Part("d6_dob") d6_dob: RequestBody,
        @Part("d6_contact_no") d6_contact_no: RequestBody,
        @Part("d6_email") d6_email: RequestBody,
        @Part("d6_aadhar_no") d6_aadhar_no: RequestBody,
        @Part("d6_address") d6_address: RequestBody,
        @Part("d6_building_no") d6_building_no: RequestBody,
        @Part("d6_postoffice") d6_postoffice: RequestBody,
        @Part("d6_locality") d6_locality: RequestBody,
        @Part("d6_state") d6_state: RequestBody,
        @Part("d6_city") d6_city: RequestBody,
        @Part d6_profile: MultipartBody.Part?,

        // Remaining variables
        @Part("ppb_address") ppb_address: RequestBody,
        @Part("ppb_building") ppb_building: RequestBody,
        @Part("ppb_postoffice") ppb_postoffice: RequestBody,
        @Part("ppb_locality") ppb_locality: RequestBody,
        @Part("ppb_state") ppb_state: RequestBody,
        @Part("bank_account") bank_account: RequestBody,
        @Part("ifsc_code") ifsc_code: RequestBody,
        @Part("acc_type") acc_type: RequestBody,

        @Part("product1") product1: RequestBody,
        @Part("product2") product2: RequestBody,
        @Part("product3") product3: RequestBody,
        @Part("product4") product4: RequestBody,
        @Part("product5") product5: RequestBody,

        @Part("prossession") prossession: RequestBody,
        @Part("buss_activity") buss_activity: RequestBody,
        @Part("authorised_sign") authorised_sign: RequestBody,
        @Part("place") place: RequestBody,

        // Docs Uploads
        @Part docs1: MultipartBody.Part?,
        @Part docs2: MultipartBody.Part?,
        @Part docs3: MultipartBody.Part?,
        @Part docs4: MultipartBody.Part?,
        @Part("ticket_rise") ticket_rise: RequestBody = "1".toRequestBody(),
    ): Observable<RegularResponse>



    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    fun legalDetails(
        @Field("info") info: String,
        @Field("customerno") customerno: String,
        @Field("legalServices") legalServices: String = "legalServices",
    ): Observable<SystemResponse<List<Any?>?>>


    @Multipart
    @POST("Backend/Merchant/MemberOperation/AddMemberNew.php")
    fun addNewMember(
        @Part("u_type") u_type: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("debit_wallet") debit_wallet: RequestBody,
        @Part("credit_wallet") credit_wallet: RequestBody,
        @Part("fname") fname: RequestBody,
        @Part("lname") lname: RequestBody,
        @Part("email") email: RequestBody,
        @Part("address") address: RequestBody,
        @Part("city") city: RequestBody,
        @Part("state") state: RequestBody,
        @Part("pincode") pincode: RequestBody,
        @Part("status") status: RequestBody,
        @Part("password") password: RequestBody,
        @Part("firm_name") firm_name: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("Landmark") Landmark: RequestBody,
        @Part("aadhar_no") aadhar_no: RequestBody,
        @Part("pan_no") pan_no: RequestBody,
        @Part("gst_no") gst_no: RequestBody,
        @Part aadhar_front: MultipartBody.Part?,
        @Part aadhar_back: MultipartBody.Part?,
        @Part pancard: MultipartBody.Part?
    ): Observable<RegularResponse>


}
