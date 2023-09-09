package com.fintech.superadmin.data.network;

import com.fintech.superadmin.activities.mahagrm_bc.BCRegisterResponse;
import com.fintech.superadmin.clean.data.remote.dto.addMember.MemberDataDto;
import com.fintech.superadmin.clean.data.remote.dto.browsePlan.BrowsePlanDto;
import com.fintech.superadmin.data.DetailedDto;
import com.fintech.superadmin.data.DynamicROfferResponse;
import com.fintech.superadmin.data.ROfferPlan;
import com.fintech.superadmin.data.accountop.AccountOPeningResponse;
import com.fintech.superadmin.data.apiResponse.fingpay.aadharOTP.SendOtpResp;
import com.fintech.superadmin.data.apiResponse.fingpay.aeps.AePsResponse;
import com.fintech.superadmin.data.apiResponse.fingpay.boad.OnboardPanResponse;
import com.fintech.superadmin.data.apiResponse.merchant.FingPayBoardCred;
import com.fintech.superadmin.data.bbpsresponse.BBPSOPResponse;
import com.fintech.superadmin.data.bc_response.BcDistrictDto;
import com.fintech.superadmin.data.bc_response.BcStateDto;
import com.fintech.superadmin.data.browseplan.BrowsePlanResponse;
import com.fintech.superadmin.data.cashfree.CashFreeLatestGateway;
import com.fintech.superadmin.data.db.entities.AuthData;
import com.fintech.superadmin.data.deer_response.CFPOTPResponse;
import com.fintech.superadmin.data.deer_response.CFPStatusCheck;
import com.fintech.superadmin.data.deer_response.CFPTransaction;
import com.fintech.superadmin.data.deer_response.RechargeResponse;
import com.fintech.superadmin.data.dthinfo.DthInfoResponse;
import com.fintech.superadmin.data.dto.AtmProceedableDto;
import com.fintech.superadmin.data.dto.CmsResponse;
import com.fintech.superadmin.data.dto.CreditCardHistory;
import com.fintech.superadmin.data.dto.MahagramResponse;
import com.fintech.superadmin.data.dto.PaysprintResponse;
import com.fintech.superadmin.data.eko.BaseEkoResponse;
import com.fintech.superadmin.data.eko.EkoAddBeneResponse;
import com.fintech.superadmin.data.eko.EkoDmtFerchBeneficaryResponse;
import com.fintech.superadmin.data.eko.EkoDmtVerificationResponse;
import com.fintech.superadmin.data.eko.EkoDtmTransactionHistory;
import com.fintech.superadmin.data.eko.EkoSendOtpResponse;
import com.fintech.superadmin.data.fastag.FastagBillFetch;
import com.fintech.superadmin.data.hlr.HLRResponse;
import com.fintech.superadmin.data.model.AEPSBanksModel;
import com.fintech.superadmin.data.model.BankModel;
import com.fintech.superadmin.data.model.ColorModel;
import com.fintech.superadmin.data.model.ComPackageModel;
import com.fintech.superadmin.data.model.EkoBankModel;
import com.fintech.superadmin.data.model.GraphModel;
import com.fintech.superadmin.data.model.HelpSupport;
import com.fintech.superadmin.data.model.HistoryModel;
import com.fintech.superadmin.data.model.OperatorModel;
import com.fintech.superadmin.data.model.PayoutUserModel;
import com.fintech.superadmin.data.model.SliderItem;
import com.fintech.superadmin.data.model.TicketHistoryModel;
import com.fintech.superadmin.data.network.dmt.FetchBeneficiaryResponse;
import com.fintech.superadmin.data.network.responses.AddBeneficiaryResponse;
import com.fintech.superadmin.data.network.responses.AePSBalanceEnquiryResponse;
import com.fintech.superadmin.data.network.responses.AnalCardReport;
import com.fintech.superadmin.data.network.responses.AnalyticsResponseModel;
import com.fintech.superadmin.data.network.responses.AuthResponse;
import com.fintech.superadmin.data.network.responses.BankItResponse;
import com.fintech.superadmin.data.network.responses.BeneHistoryUpdateResponse;
import com.fintech.superadmin.data.network.responses.BeneficiaryDeleteResponse;
import com.fintech.superadmin.data.network.responses.BeneficiaryHistoryResponse;
import com.fintech.superadmin.data.network.responses.BusBookRedirect;
import com.fintech.superadmin.data.network.responses.CCGenerateOtpResponse;
import com.fintech.superadmin.data.network.responses.CommissionResponse;
import com.fintech.superadmin.data.network.responses.ConfirmationResponse;
import com.fintech.superadmin.data.network.responses.CustomerInfoResponse;
import com.fintech.superadmin.data.network.responses.DMTSendAmountResponse;
import com.fintech.superadmin.data.network.responses.DetailedHistoryResponse;
import com.fintech.superadmin.data.network.responses.DmtUserData;
import com.fintech.superadmin.data.network.responses.FetchBillInfo;
import com.fintech.superadmin.data.network.responses.GatewayResponse;
import com.fintech.superadmin.data.network.responses.LicFetchResponse;
import com.fintech.superadmin.data.network.responses.MiniStatementResponse;
import com.fintech.superadmin.data.network.responses.MyOfferResponse;
import com.fintech.superadmin.data.network.responses.NumberPayResponse;
import com.fintech.superadmin.data.network.responses.OnlineFundResponse;
import com.fintech.superadmin.data.network.responses.OperatorResponse;
import com.fintech.superadmin.data.network.responses.PackageResponseData;
import com.fintech.superadmin.data.network.responses.PayoutAddResponse;
import com.fintech.superadmin.data.network.responses.PayoutListResponse;
import com.fintech.superadmin.data.network.responses.PayoutResponse;
import com.fintech.superadmin.data.network.responses.PaytmTokenInformation;
import com.fintech.superadmin.data.network.responses.PennyDropResponse;
import com.fintech.superadmin.data.network.responses.PlanType;
import com.fintech.superadmin.data.network.responses.QRResponse;
import com.fintech.superadmin.data.network.responses.QueryRemitterResponse;
import com.fintech.superadmin.data.network.responses.RegisterRemitterResponse;
import com.fintech.superadmin.data.network.responses.RegularHistoryResponse;
import com.fintech.superadmin.data.network.responses.RegularResponse;
import com.fintech.superadmin.data.network.responses.SlabInfoResponse;
import com.fintech.superadmin.data.network.responses.SystemResponse;
import com.fintech.superadmin.data.network.responses.UTIPANRedirect;
import com.fintech.superadmin.data.network.responses.UserTypeResponse;
import com.fintech.superadmin.data.sys.TPinResponse;
import com.fintech.superadmin.data_model.LoginModel;
import com.fintech.superadmin.data_model.offline.OfflineBank;
import com.fintech.superadmin.data_model.request.RequestedHistoryModel;
import com.fintech.superadmin.model.PayuResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIServices {

    @FormUrlEncoded
    @POST("Backend/Merchant/MemberOperation/Login/Login.php")
    Observable<AuthData> getUserLogin(
            @Field("mobile") String mobileX,
            @Field("password") String password,
            @Field("long") String longitude,
            @Field("lati") String latitude
    );


    @FormUrlEncoded
    @POST("Agent/Backend/Login/register.php")
    Observable<RegularResponse> userSignup(@Field("fname") String first_name, @Field("lname") String last_name,
                                           @Field("mobile") String mobile, @Field("email") String email,
                                           @Field("password") String password, @Field("otp") String otp,
                                           @Field("referal_code") String referal_code);

    @FormUrlEncoded
    @POST("Agent/Backend/Login/send_otp.php")
    Observable<RegularResponse> sendSignupOTP(@Field("mobile") String mobile, @Field("email") String email);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<SystemResponse<String>> magicWalletStatus(
            @Field("magicWalletStatus") String magicWalletStatus
    );

    @FormUrlEncoded
    @POST("Backend/Merchant/360_Wallet/magic_active.php")
    Observable<SystemResponse<String>> magicWalletActivate(
            @Field("activateWallet") String activateWallet
    );


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<CmsResponse> startCMS(
            @Field("cms") String cms,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );


    @FormUrlEncoded
    @POST("Backend/Merchant/Railway/Railway.php")
    Observable<RegularResponse> startRailways(
            @Field("flight") String flight
    );

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<BusBookRedirect> busRedirect(
            @Field("busRedirect") String busRedirect
    );

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<UTIPANRedirect> utiPanCardRedirect(
            @Field("utiPanCard") String utiPanCard
    );

    @FormUrlEncoded
    @POST("Backend/Merchant/API/QrSystem/main.php")
    Observable<SystemResponse<String>> showQrSystem(
            @Field("displayMyQrCode") String displayMyQrCode
    );

    @FormUrlEncoded
    @POST("Backend/Merchant/API/QrSystem/main.php")
    Observable<RegularResponse> applyForQrCode(
            @Field("createVpa") String createVpa
    );


    @FormUrlEncoded
    @POST("on_boarding/my_onbarding.php")
    Observable<String> onSuccessOnBoarding(@Field("mobile") String mobile, @Field("owner") String owner, @Field("owner_id") String owner_id, @Field("partner_id") String partner_id, @Field("merchant_code") String merchant_code, @Field("code") String code);


    //Bank Details

    @Multipart
    @POST("Backend/Merchant/API/app/temp/main.php?apicall=upload")
    Observable<RegularResponse> updateBankDetails(@Part MultipartBody.Part aadhaar,
                                                  @Part MultipartBody.Part pan,
                                                  @Part MultipartBody.Part passbook,
                                                  @Part("bankDetails") RequestBody bankDetails);
    //Bank Details


    //HLR CHECK
    @FormUrlEncoded
    @POST("Backend/Merchant/API/Recharge/PaySprint/hlr.php")
    Observable<HLRResponse> getHLRInformation(@Field("service_type") String service_type, @Field("type") String type, @Field("number") String number,
                                              @Field("hlr_check") String hlr_check);
    //HLR CHECK


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<RegularResponse> updateProfilePicture(@Field("profile_picture") String profile_picture);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<SystemResponse<LoginModel>> getAllCredentials(@Field("fetch_all") String fetch_all);

    //CFPayout

    @FormUrlEncoded
    @POST("Agent/Backend/PAYOUT/cashfree/main.php")
    Observable<SystemResponse<CFPOTPResponse>> cfPayoutSendOtp(@Field("amount") String amount, @Field("sendotp") String sendotp);

    @FormUrlEncoded
    @POST("Agent/Backend/PAYOUT/cashfree/main.php")
    Observable<SystemResponse<CFPTransaction>> cfPayoutSendAmount(@Field("send_amount") String send_amount, @Field("otp") String otp,
                                                                  @Field("verify") String verify);

    @FormUrlEncoded
    @POST("Agent/Backend/PAYOUT/cashfree/main.php")
    Observable<SystemResponse<CFPStatusCheck>> cfPayoutStatus(@Field("ref_id") String ref_id, @Field("check_status") String check_status);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<SystemResponse<PayoutUserModel>> getPayoutUserValues(@Field("payout_account_disp") String payout_account_disp);
    //


    //Virtual fetch

    @FormUrlEncoded
    @POST("Agent/Backend/DMT/cashfree/main.php")
    Observable<SystemResponse<CFPStatusCheck>> cfpfmtCheckStatus(@Field("ref_id") String ref_id, @Field("check_dmt_status") String check_dmt_status);

    //Changing Password


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<List<OperatorModel>> getOperatorsList(@Field("operator") String operator);


    @FormUrlEncoded
    @POST("mobile_phone/micro_atm/on_boarding.php")
    Observable<RegularResponse> startOnBoarding(@Field("user_id") String user_id, @Field("first_name") String first_name, @Field("last_name") String last_name, @Field("email") String email, @Field("mobile") String mobile,
                                                @Field("aadhaar_no") String aadhaar_no, @Field("pan_no") String pan_no,
                                                @Field("serial_no") String serial_no, @Field("front_aadhaar") String front_aadhaar, @Field("back_aadhaar") String back_aadhaar,
                                                @Field("front_pan") String front_pan, @Field("back_pan") String back_pan, @Field("serial_image") String serial_image);

    @FormUrlEncoded
    @POST("mobile_phone/micro_atm/on_boarding_status.php")
    Observable<BankItResponse> bankIt_OnBoardStatus(@Field("user_id") String user_id, @Field("password") String password, @Field("token") String token);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/Recharge/PaySprint/recharge.php")
    Observable<RechargeResponse> makeThePayment(@Field("service_type") String service_type, @Field("recharge_mobile") String recharge_mobile, @Field("recharge_amount") String recharge_amount,
                                                @Field("recharge_operator") String recharge_operator, @Field("tpin") String tpin);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/Recharge/PaySprint/recharge.php")
    Observable<RegularResponse> prepaidRecharge_Status(@Field("ref_id") String ref_id,
                                                       @Field("check_status") String check_status);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/Recharge/PaySprint/recharge.php")
    Observable<BrowsePlanResponse> browsePlan(@Field("service_type") String service_type, @Field("op") String op, @Field("circle") String circle, @Field("check_plan") String check_plan);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/Recharge/PaySprint/hlr.php")
    Observable<SystemResponse<Object>> dth_info(@Field("service_type") String service_type, @Field("number") String number, @Field("operatorCode") String operatorCode, @Field("dth_info") String dth_info);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/BBPS/PaySprint/fetchDetails.php")
    Observable<BBPSOPResponse> fetchBBpsOperators(@Field("fetch_operators") String fetch_operators);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/BBPS/PaySprint/fetchDetails.php")
    Observable<FetchBillInfo> fetchBBpsBill(@Field("num") String num, @Field("op") String op,
                                            @Field("billingUnit") String billingUnit, @Field("ad1") String ad1,
                                            @Field("ad2") String ad2, @Field("ad3") String ad3, @Field("opData") String bbpsopData);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/BBPS/PaySprint/fetchDetails.php")
    Observable<SystemResponse<List<String>>> fetchBBPSMode(@Field("modesList") String modesList);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/BBPS/PaySprint/bill_pay.php")
    Observable<RegularResponse> payBBpsBill(@Field("amount") String amount, @Field("num") String num, @Field("op") String op,
                                            @Field("billdata") String fetchBillInfo,
                                            @Field("op_name") String op_name, @Field("category") String category,
                                            @Field("tpin") String tpin, @Field("long") String longi, @Field("lati") String lati, @Field("typeMode") String typeMode);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/Payout/PaySprint/main.php")
    Observable<ArrayList<BankModel>> getAllBanks(@Field("getBanks") String getBanks);


    @FormUrlEncoded
    @POST("mobile_phone/histories/default_history.php")
    Observable<List<HistoryModel>> getHistory(@Field("user_id") String user_id, @Field("user_type_id") String user_type_id, @Field("indexing") String indexing, @Field("fromDate") String fromDate, @Field("toDate") String toDate, @Field("transType") String transType, @Field("result") String result, @Field("id") String id);

    @FormUrlEncoded
    @POST("mobile_phone/predictions/predict.php")
    Observable<CommissionResponse> getCommission(@Field("user_id") String user_id, @Field("user_type") String user_type, @Field("pay_type") String pay_type, @Field("amount") String amount);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<RegularResponse> numberAuthenticate(@Field("mobile") String mobile, @Field("sendPayAvailable") String sendPayAvailable);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/SelfPay/main.php")
    Observable<RegularResponse> numberAuthenticatePay(@Field("mobile") String mobile, @Field("amount") String amount);


    @FormUrlEncoded
    @POST("Agent/Backend/Calls/custom.php")
    Observable<RegularResponse> onBoardingStatus(@Field("merchantcode") String merchantcode, @Field("event") String event);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<PaysprintResponse> paysprintTypeExistence(@Field("paysprintv2") String paysprint);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<FingPayBoardCred> fingpayExistence(@Field("fingpayv2") String fingpay);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<MahagramResponse> mahagramTypeExistence(@Field("mahagramv2") String mahagram);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/PaySprint/getBankList.php")
    Observable<List<AEPSBanksModel>> getPaysprintBankList(@Field("id") String id);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/FingPay/Txns.php")
    Observable<List<AEPSBanksModel>> getFingPayBankList(@Field("getMappedBanks") String getMappedBanks);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/FingPay/Txns.php")
    Observable<AePsResponse> AEPSFingPayResponse(@Field("device") String app, @Field("aadhar") String aadhar, @Field("fingerData") String fingerData, @Field("mobile") String mobile, @Field("transType") String transType, @Field("bankName") String bankName, @Field("long") String longitude, @Field("lat") String latitude, @Field("amount") String amount);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/PaySprint/aeps_init_req.php")
    Observable<AePSDto> AEPSResponse(@Field("bank") String bank, @Field("device") String app, @Field("aadhar") String aadhar, @Field("fingerData") String fingerData,
                                     @Field("mobile") String mobile, @Field("transType") String transType,
                                     @Field("bankName") String bankName, @Field("long") String longitude,
                                     @Field("lat") String latitude, @Field("amount") String amount);



    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/PaySprint/aeps_init_req.php")
    Observable<RegularResponse> AePSRegistrationAuthenticate(@Field("device") String app, @Field("aadharReg") String aadharReg, @Field("fingerData") String fingerData,
                                                             @Field("long") String longitude, @Field("lat") String latitude);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/PaySprint/aeps_init_req.php")
    Observable<RegularResponse> AePSDailyAuthenticate(@Field("device") String app, @Field("AuthToday") String AuthToday, @Field("fingerData") String fingerData,
                                                      @Field("long") String longitude, @Field("lat") String latitude);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<RegularResponse> aEPSDailyAuthCheck(@Field("aePSDailyAuth") String aePSDailyAuth);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<RegularResponse> aEPSRegistrationAuthCheck(@Field("aePSReg") String aePSReg);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/PaySprint/aeps_init_req.php")
    Observable<MiniStatementResponse> startAepsResponseMiniStatement(@Field("device") String app, @Field("aadhar") String aadhar, @Field("fingerData") String fingerData,
                                                                     @Field("mobile") String mobile, @Field("transType") String transType,
                                                                     @Field("bankName") String bankName, @Field("long") String longitude,
                                                                     @Field("lat") String latitude, @Field("amount") String amount);


    @FormUrlEncoded
    @POST("/Backend/Merchant/API/AePs/PaySprint/aeps_init_req.php")
    Observable<RegularResponse> aEPS_Status(@Field("ref_id") String ref_id, @Field("check_aeps_status") String check_aeps_status);


    @FormUrlEncoded
    @POST("mobile_phone/signpage/re_authenticate.php")
    Observable<AuthResponse> reAuthenticateUser(@Field("mobile") String mobile, @Field("password") String password, @Field("token_id") String token_id);


    @FormUrlEncoded
    @POST("mobile_phone/paytm/paytm.php")
    Observable<PayoutResponse> payThroughPayouts(@Field("amount") String amount, @Field("bank") String bank, @Field("ifsc") String ifsc, @Field("trans") String trans, @Field("id") String id, @Field("usertype_id") String usertype_id, @Field("ip_address") String ip_address, @Field("device") String device);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<ConfirmationResponse> updateMyInformation(@Field("f_name") String f_name, @Field("l_name") String l_name, @Field("a_number") String a_number, @Field("dob") String dob, @Field("gender") String gender, @Field("country") String country, @Field("state") String state, @Field("pin") String pin, @Field("address") String address, @Field("token") String token, @Field("mobile") String mobile, @Field("update_profile_details") String update_profile_details);

    @FormUrlEncoded
    @POST("mobile_phone/add_fund/add_fund_online.php")
    Observable<OnlineFundResponse> onlineAddFundProcess(@Field("online_fund") String online_fund, @Field("order_id") String order_id, @Field("mobile") String mobile, @Field("password") String password, @Field("amount") String amount, @Field("status") String status, @Field("device") String device, @Field("ip_address") String ip_address);


    @FormUrlEncoded
    @POST("Agent/Backend/razorpay/main.php")
    Observable<GatewayResponse> getPaymentCredentials(@Field("gateway") String gateway, @Field("amount") String amount);


    @FormUrlEncoded
    @POST("Agent/Backend/Login/update_data.php")
    Observable<ConfirmationResponse> updateMySocialMedia(@Field("mobile") String mobile, @Field("update_social_media") String update_social_media,
                                                         @Field("facebook_url") String facebook_url, @Field("twitter_url") String twitter_url, @Field("linkedin_url") String linkedin_url,
                                                         @Field("instagram_url") String instagram_url, @Field("dribble_box_url") String dribble_box_url, @Field("dropbox_url") String dropbox_url,
                                                         @Field("google_plus") String google_plus, @Field("pintrest") String pintrest, @Field("skype_url") String skype_url, @Field("vine_url") String vine_url);

    @FormUrlEncoded
    @POST("Agent/Backend/Login/update_data.php")
    Observable<ConfirmationResponse> changeMyPassword(
            @Field("update_my_password") String update_my_password,
            @Field("new_password") String new_password,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("Agent/Backend/paytm_addfund/init_Transaction.php")
    Observable<PaytmTokenInformation> generateTokenCall(@Field("code") String code, @Field("ORDER_ID") String oid, @Field("AMOUNT") String amount);


    @FormUrlEncoded
    @POST("mobile_phone/micro_atm/atm_services.php")
    Observable<String> insertReportForMicroAtm(@Field("message") String message, @Field("response") String response, @Field("transAmount") String transAmount, @Field("balAmount") String balAmount, @Field("bankRrn") String balRrn,
                                               @Field("txnid") String txnId, @Field("transType") String transType, @Field("type") String type, @Field("cardNumber") String cardNumber, @Field("cardType") String cardType,
                                               @Field("terminalId") String terminalId, @Field("bankName") String bankName, @Field("user_id") String user_id, @Field("user_status") String user_status, @Field("reference") String reference, @Field("boolstatus") String boolstatus);

    @FormUrlEncoded
    @POST("mobile_phone/creation/user_types.php")
    Observable<UserTypeResponse> bringAllSuitableUserType(@Field("id") String id, @Field("userType") String userType);


    //Dmt Paysprint


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<QueryRemitterResponse> queryRemitter(@Field("mobile") String mobile, @Field("send_otp") String send_otp);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<RegisterRemitterResponse> registerRemitter(@Field("otp") String otp, @Field("str_code") String str_code,
                                                          @Field("fname") String remitter_first_name, @Field("lname") String remitter_last_name,
                                                          @Field("mobile") String mobile, @Field("Address") String Address, @Field("pincode") String pincode,
                                                          @Field("dob") String dob);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<RegisterRemitterResponse> updateRemitter(@Field("remitter_first_name") String remitter_first_name, @Field("remitter_last_name") String remitter_last_name,
                                                        @Field("remitter_mobile") String remitter_mobile,
                                                        @Field("dob") String dob, @Field("address") String address,
                                                        @Field("pincode") String pincode, @Field("updateRemitter") String updateRemitter);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<AddBeneficiaryResponse> registerBeneficiary(@Field("bene_name") String bene_name, @Field("bene_acc") String bene_acc, @Field("bene_ifsc") String bene_ifsc, @Field("bene_bank") String bene_bank, @Field("senderMobile") String senderMobile);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<FetchBeneficiaryResponse> bringBeneficiary(@Field("mobile") String mobile, @Field("acc_num") String acc_num, @Field("get_beneficiaries") String get_beneficiaries);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<PennyDropResponse> getThePennyDrop(
            @Field("beneid") String beneid,
            @Field("bene_acc") String bene_acc,
            @Field("bank_code") String bank_code,
            @Field("benename") String benename,
            @Field("senderMobile") String senderMobile,
            @Field("verify_bene") String verify_bene
    );


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<BeneficiaryDeleteResponse> deleteBeneficiary(@Field("bene_id") String bene_id, @Field("bene_acc") String bene_acc, @Field("bene_delete") String bene_delete, @Field("senderMobile") String senderMobile);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<DMTSendAmountResponse> sendAmountDMT(@Field("tpin") String tpin, @Field("bene_id") String bene_id, @Field("send_amount") String send_amount, @Field("send_am_acc") String send_am_acc, @Field("txn_type") String txn_type, @Field("ifsc") String ifsc, @Field("senderMobile") String senderMobile);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<List<BeneficiaryHistoryResponse>> getSelectedBeneficiaryHistory(@Field("reference") String reference, @Field("bene_id") String bene_id, @Field("selectedHistory") String selectedHistory);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<BeneHistoryUpdateResponse> updateDMTTransaction(@Field("ref_id") String ref_id, @Field("check_dmt_status") String check_dmt_status);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<List<BeneficiaryHistoryResponse>> getAllBeneficiaryHistories(@Field("acc_num") String num_acc, @Field("senderMobile") String senderMobile, @Field("all_histories") String all_histories);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<List<DmtUserData>> getAllDMTUsers(@Field("person") String person, @Field("dmt_acc") String dmt_acc);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<RegularResponse> deleteDmtUserAccount(@Field("delete_id") String delete_id, @Field("delete_dmt_user") String delete_dmt_user);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<QueryRemitterResponse> refreshDmtUserAccount(@Field("refresh_id") String refresh_id, @Field("mobile") String mobile, @Field("refresh_dmt_user") String refresh_dmt_user);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<RegularResponse> refundDmtTransaction(@Field("akno") String ackno, @Field("ref_id") String refrence, @Field("resendRefundOTP") String resendRefundOTP);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/PaySprint/Main.php")
    Observable<RegularResponse> verifyDmtTransaction(@Field("akno") String ackno, @Field("ref_id") String refrence, @Field("refund_otp") String otp, @Field("refundDmt") String refundDmt);

    //paysprint dmt


    //Dmt Eko


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<ArrayList<EkoBankModel>> getAllEkoBanks(@Field("getEkoBanks") String getBanks);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<com.fintech.superadmin.data.eko.QueryRemitterResponse> ekoqueryRemitter(@Field("mobile") String mobile, @Field("getInfoBtn") String getInfoBtn);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<EkoSendOtpResponse> ekoregisterRemitter(@Field("send_otp") String send_otp,
                                                       @Field("fname") String fname, @Field("lname") String lname,
                                                       @Field("mobile") String mobile, @Field("Address") String Address, @Field("pincode") String pincode,
                                                       @Field("dob") String dob);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<EkoDmtVerificationResponse> ekoVerifyRemitter(@Field("str_code") String str_code, @Field("otp") String otp,
                                                             @Field("fname") String fname, @Field("lname") String lname,
                                                             @Field("mobile") String mobile, @Field("Address") String Address, @Field("pincode") String pincode,
                                                             @Field("dob") String dob);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<RegisterRemitterResponse> ekoupdateRemitter(@Field("remitter_first_name") String remitter_first_name, @Field("remitter_last_name") String remitter_last_name,
                                                           @Field("remitter_mobile") String remitter_mobile,
                                                           @Field("dob") String dob, @Field("address") String address,
                                                           @Field("pincode") String pincode, @Field("updateRemitter") String updateRemitter);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<EkoAddBeneResponse> ekoregisterBeneficiary(@Field("bene_name") String bene_name, @Field("bene_acc") String bene_acc, @Field("bene_ifsc") String bene_ifsc, @Field("bene_bank") String bene_bank, @Field("sendermobile") String sendermobile);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<EkoDmtFerchBeneficaryResponse> ekobringBeneficiary(@Field("mobile") String mobile, @Field("acc_num") String acc_num, @Field("get_beneficiaries") String get_beneficiaries);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<BaseEkoResponse> ekogetThePennyDrop(
            @Field("beneid") String beneid,
            @Field("bene_acc") String bene_acc,
            @Field("bank_code") String bank_code,
            @Field("benename") String benename,
            @Field("senderMobile") String senderMobile,
            @Field("verify_bene") String verify_bene
    );


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<BaseEkoResponse> ekodeleteBeneficiary(@Field("bene_id") String bene_id, @Field("bene_acc") String bene_acc, @Field("bene_delete") String bene_delete, @Field("senderMobile") String senderMobile);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<DMTSendAmountResponse> ekosendAmountDMT(@Field("tpin") String tpin, @Field("bene_id") String bene_id, @Field("send_amount") String send_amount, @Field("send_am_acc") String send_am_acc, @Field("txn_type") String txn_type, @Field("ifsc") String ifsc, @Field("senderMobile") String senderMobile);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<List<EkoDtmTransactionHistory>> ekogetSelectedBeneficiaryHistory(@Field("reference") String reference, @Field("bene_id") String bene_id, @Field("selectedHistory") String selectedHistory);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<BeneHistoryUpdateResponse> ekoupdateDMTTransaction(@Field("ref_id") String ref_id, @Field("check_dmt_status") String check_dmt_status);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<List<EkoDtmTransactionHistory>> ekogetAllBeneficiaryHistories(@Field("acc_num") String num_acc, @Field("senderMobile") String senderMobile, @Field("all_histories") String all_histories);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<List<DmtUserData>> ekogetAllDMTUsers(@Field("person") String person, @Field("dmt_acc") String dmt_acc);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<RegularResponse> ekodeleteDmtUserAccount(@Field("delete_id") String delete_id, @Field("delete_dmt_user") String delete_dmt_user);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<QueryRemitterResponse> ekorefreshDmtUserAccount(@Field("refresh_id") String refresh_id, @Field("mobile") String mobile, @Field("refresh_dmt_user") String refresh_dmt_user);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<RegularResponse> ekorefundDmtTransaction(@Field("akno") String ackno, @Field("ref_id") String refrence, @Field("resendRefundOTP") String resendRefundOTP);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/MoneyTransfer/EKO/Main.php")
    Observable<RegularResponse> ekoverifyDmtTransaction(@Field("akno") String ackno, @Field("ref_id") String refrence, @Field("refund_otp") String otp, @Field("refundDmt") String refundDmt);

    //eko dmt


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/analytic_hist.php")
    Observable<List<AnalyticsResponseModel>> getAllAnalytics(@Field("user_id") String user_id, @Field("user_type_id") String user_type_id, @Field("indexing") String indexing, @Field("fromDate") String fromDate, @Field("toDate") String toDate, @Field("transType") String transType, @Field("result") String result, @Field("id") String id);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/complete_hist_detail.php")
    Observable<DetailedHistoryResponse> getMyAnalyticDetailed(@Field("report_id") String report_id, @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/transactionDetails.php")
    Observable<SystemResponse<DetailedDto>> getDetailedReport(@Field("report_id") String report_id, @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/Recharge/PaySprint/hlr.php")
    Observable<SystemResponse<List<ROfferPlan>>> getMeROffers(@Field("service_type") String service_type, @Field("operatorCode") String operatorCode, @Field("number") String number, @Field("r_offer") String r_offer);

    @FormUrlEncoded
    @POST("mobile_phone/recharge/browseplan.php")
    Observable<PlanType> getbrowseplan(@Field("operator") String operator);

    @FormUrlEncoded
    @POST("mobile_phone/recharge/roffer.php")
    Observable<MyOfferResponse> getMeDthROffers(@Field("op") String op, @Field("num") String num, @Field("user_id") String user_id, @Field("password") String password, @Field("dth_browse_plan") String dth_browse_plan);

    @FormUrlEncoded
    @POST("mobile_phone/recharge/roffer.php")
    Observable<CustomerInfoResponse> getDthCustomerInfo(@Field("op") String op, @Field("num") String num, @Field("user_id") String user_id, @Field("password") String password, @Field("dth_customer_info") String dth_customer_info);

    @FormUrlEncoded
    @POST("Agent/Backend/Login/forgot_password.php")
    Observable<RegularResponse> forgotMyPassword(@Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("Agent/Backend/Login/change_password.php")
    Observable<RegularResponse> changeMyPasswordStart(@Field("mobile") String mobile, @Field("otp") String otp, @Field("password") String password);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<List<SliderItem>> getMeBanners(@Field("banners") String banners);


    //update_check status
    @FormUrlEncoded
    @POST("Agent/Backend/status/update.php")
    Observable<RegularResponse> getUpdatesOnTransaction(@Field("transactionType") String type, @Field("reference_id") String reference_id);

    @FormUrlEncoded
    @POST("mobile_phone/t_pin/tpin_check.php")
    Observable<RegularResponse> TpinSecuritySystem(@Field("user_id") String user_id, @Field("password") String password, @Field("token") String token);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<RegularResponse> exchangeWallet(@Field("walletExchange") String walletExchange, @Field("amount") String amount);


    @Multipart
    @POST("Backend/Merchant/Ledger/CashDeposit.php?apicall=upload")
    Observable<RegularResponse> offlineWalletDemand(
            @Part MultipartBody.Part receipt,
            @Part("bank") RequestBody bank,
            @Part("amount") RequestBody amount,
            @Part("payment_mode") RequestBody payment_mode,
            @Part("txnid") RequestBody txnid,
            @Part("remark") RequestBody remark,
            @Part("WalletRechargeOffline") RequestBody WalletRechargeOffline
    );

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<SystemResponse<List<OfflineBank>>> getOfflineBank(@Field("offline_banks") String offline_banks);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<RegularResponse> getMeLatestNews(@Field("newsAlert") String newsAlert);

    //Bring Packages
    @FormUrlEncoded
    @POST("mobile_phone/packages/self_comm.php")
    Observable<PackageResponseData> getAllPackageList(@Field("user_id") String user_id, @Field("user_type") String user_type,
                                                      @Field("token") String token, @Field("pack_type") String pack_type, @Field("fetch") String fetch);

    @FormUrlEncoded
    @POST("mobile_phone/packages/self_comm.php")
    Observable<RegularResponse> setThisPackage(@Field("user_id") String user_id, @Field("user_type") String user_type,
                                               @Field("token") String token, @Field("selectedId") String selectedId, @Field("pack_type") String pack_type, @Field("set_package") String set_package);

    @FormUrlEncoded
    @POST("mobile_phone/packages/getSlabList.php")
    Observable<ArrayList<SlabInfoResponse>> getThisPackageSlabData(@Field("package_id") String package_id, @Field("fetch_slab") String fetch_slab);

    //Bring Packages


    //Analytic Section
    @FormUrlEncoded
    @POST("mobile_phone/deep_analytics/analytic_info.php")
    Observable<AnalCardReport> getAnalyticCardData(@Field("user_id") String user_id, @Field("user_type") String user_type,
                                                   @Field("token") String token,
                                                   @Field("type") String type, @Field("on_day") String on_day);

    @FormUrlEncoded
    @POST("mobile_phone/deep_analytics/analytic_graph.php")
    Observable<ArrayList<GraphModel>> getAnalyticGraphData(@Field("user_id") String user_id, @Field("user_type") String user_type,
                                                           @Field("token") String token, @Field("type") String type,
                                                           @Field("on_day") String on_day, @Field("fromDate") String fromDate, @Field("toDate") String toDate);
    //Analytic Section


    //Pay_sprint_payout

    @FormUrlEncoded
    @POST("Backend/Merchant/API/Payout/PaySprint/main.php")
    Observable<PayoutListResponse> paySprintPayoutList(@Field("list") String list);


    @Multipart
    @POST("Backend/Merchant/API/Payout/PaySprint/main.php?apicall=upload")
    Observable<RegularResponse> verifyPayoutAccount(
            @Part MultipartBody.Part afront, @Part MultipartBody.Part aback,
            @Part MultipartBody.Part passbook, @Part("accID") RequestBody beneID,
            @Part("doctype") RequestBody doctype
    );

    @Multipart
    @POST("Backend/Merchant/API/Payout/PaySprint/main.php?apicall=upload")
    Observable<RegularResponse> verifyPayoutAccount(
            @Part MultipartBody.Part pan, @Part MultipartBody.Part passbook,
            @Part("accID") RequestBody beneID, @Part("doctype") RequestBody doctype
    );


    @FormUrlEncoded
    @POST("Backend/Merchant/API/Payout/PaySprint/main.php")
    Observable<RegularResponse> checkVerificationStatus(
            @Field("bene_id") String bene_id, @Field("checkVerifyStatus") String checkVerifyStatus);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/Payout/PaySprint/main.php")
    Observable<PayoutAddResponse> paySprintPayoutAddAccount(@Field("bankName") String bankName, @Field("accountNum") String accountNum,
                                                            @Field("ifsc") String ifsc, @Field("Name") String Name);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/Payout/PaySprint/main.php")
    Observable<RegularResponse> sendMoneyOnPayAmount(@Field("tpin") String tpin, @Field("accID") String bene_id, @Field("amount") String amount,
                                                     @Field("mode") String mode, @Field("Account") String Account, @Field("IFSC") String IFSC);

    //Pay_sprint_payout


    //usualHistory
    @FormUrlEncoded
    @POST("mobile_phone/usual_history/get_history.php")
    Observable<RegularHistoryResponse> getRegularHistoryResp(@Field("user_id") String user_id, @Field("token") String token,
                                                             @Field("history") String history);

    @FormUrlEncoded
    @POST("mobile_phone/usual_history/get_history.php")
    Observable<RegularResponse> getRegularHistoryStatus(@Field("user_id") String user_id, @Field("token") String token,
                                                        @Field("historystatus") String historystatus, @Field("reference") String reference,
                                                        @Field("device") String device, @Field("ip") String ip);
    //usualHistory


    //

    //PaySprint_PaymentGateway


    //PaySprint_Lic
    @FormUrlEncoded
    @POST("Backend/Merchant/API/Lic/PaySprint/fetchDetail.php")
    Observable<LicFetchResponse> fetchLicBill(@Field("num") String num, @Field("op") String op, @Field("fetch_lic") String fetch_lic);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/Lic/PaySprint/main.php")
    Observable<RegularResponse> payLicBill(@Field("canum") String canum, @Field("ad1") String ad1,
                                           @Field("billDetails") String billDetails, @Field("long") String longi,
                                           @Field("lati") String lati, @Field("pay_lic") String pay_lic, @Field("mode") String mode);

    //PaySprint_Lic


    //report micro_atm txn

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<RegularResponse> getATMType(
            @Field("getAtMType") String getAtMType
    );


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<ArrayList<String>> getGatewayLists(
            @Field("getGatewayLists") String getGatewayLists
    );

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<AtmProceedableDto> reportMicroAtmTxn(
            @Field("amount") String amount, @Field("txnType") String txnType,
            @Field("initiate_atm_txn") String initiate_atm_txn);

    @FormUrlEncoded
    @POST("Backend/Merchant/MemberOperation/temp/onboard.php?tPin=yes")
    Observable<SystemResponse<TPinResponse>> checkMpinStatus(@Field("check_mpin") String check_mpin);

    @FormUrlEncoded
    @POST("Backend/Merchant/MemberOperation/temp/onboard.php?tPin=yes")
    Observable<SystemResponse<TPinResponse>> verifyMPINStatus(@Field("mpin") String mpin, @Field("verify_mpin") String check_mpin);

    @FormUrlEncoded
    @POST("Backend/Merchant/MemberOperation/temp/onboard.php?tPin=yes")
    Observable<SystemResponse<CFPOTPResponse>> sendOTPForMpin(@Field("times") String times, @Field("otp_mpin") String otp_mpin);

    @FormUrlEncoded
    @POST("Backend/Merchant/MemberOperation/temp/onboard.php?tPin=yes")
    Observable<RegularResponse> setAllMpin(@Field("m_pin") String m_pin, @Field("otp") String otp, @Field("hash_code") String hash,
                                           @Field("set_mpin") String set_mpin);

    @FormUrlEncoded
    @POST("Backend/Merchant/MemberOperation/temp/onboard.php?tPin=yes")
    Observable<RegularResponse> changeMyMpin(@Field("otp") String otp, @Field("hash_code") String hash, @Field("old_m_pin") String old_m_pin, @Field("new_m_pin") String new_m_pin,
                                             @Field("change_mpin") String change_mpin);

    //FASTAG
    @FormUrlEncoded
    @POST("Backend/Merchant/API/FasTag/PaySprint/main.php")
    Observable<OperatorResponse> getFast_agOperator(@Field("fastag_list") String fastag_list);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/FasTag/PaySprint/main.php")
    Observable<FastagBillFetch> fetchFasTagBill(@Field("ca_num") String num, @Field("fast_id") String fast_id, @Field("fetch_fast") String fetch_fast);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/FasTag/PaySprint/pay_bill.php")
    Observable<RegularResponse> payFastAgBill(@Field("tpin") String tpin, @Field("amount") String amount, @Field("num") String num, @Field("op") String op,
                                              @Field("billdata") String billdata, @Field("long") String longi,
                                              @Field("lati") String lati, @Field("pay_fastag") String pay_fastag);
    //FASTAG


    //Request History
    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<SystemResponse<List<RequestedHistoryModel>>> getRequestHistory(@Field("trans_id") String trans_id, @Field("requestedHistory") String requestedHistory);


    //

    @POST("Backend/Merchant/API/AccontOpen/main.php")
    Observable<AccountOPeningResponse> openAccount(@Query("acctype") int acctype);


    //help Support

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<HelpSupport> getHelpSupport(@Field("help_support") String help_support);

    //help Support

    //Commission Report
    @FormUrlEncoded
    @POST("Agent/Backend/MyCommission/main.php")
    Observable<ArrayList<ComPackageModel>> getComPlans(@Field("TYPE") String TYPE);


    //UPI TRANSACTION
    @FormUrlEncoded
    @POST("Agent/Backend/UPI/razorpay/main.php")
    Observable<RegularResponse> makeUpiTransaction(
            @Field("vpa_id") String vpa_id,
            @Field("sendupi_name") String sendupi_name,
            @Field("send_amount") String send_amount
    );

    //UPI TRANSACTION

    //QR CODE
    @FormUrlEncoded
    @POST("Agent/Backend/VirtualAccount/main.php")
    Observable<SystemResponse<QRResponse>> getQrCode(@Field("displayQR") String displayQR);
    //QR CODE

    //BC Registration
    @FormUrlEncoded
    @POST("Agent/Backend/AEPS/Mahagram/bc_operation.php")
    Observable<ArrayList<BcStateDto>> getBcStates(@Field("get_state") String state);

    @FormUrlEncoded
    @POST("Agent/Backend/AEPS/Mahagram/bc_operation.php")
    Observable<ArrayList<BcDistrictDto>> getBcDistrict(@Field("get_district") String district, @Field("st_id") String st_id);


    @FormUrlEncoded
    @POST("Agent/Backend/AEPS/Mahagram/bc_operation.php")
    Observable<BCRegisterResponse> doBcRegistration(
            @Field("f_name") String f_name, @Field("l_name") String l_name,
            @Field("email") String email, @Field("num") String num,
            @Field("alternate_num") String alternate_num, @Field("date_birth") String date_birth,
            @Field("state") String state, @Field("district") String district, @Field("address") String address,
            @Field("block") String block, @Field("city") String city, @Field("landmark") String landmark,
            @Field("locality") String locality, @Field("mohoalla") String mohoalla, @Field("pan_number") String pan_number,
            @Field("pincode") String pincode, @Field("shop_name") String shop_name, @Field("shop_type") String shop_type,
            @Field("qualification") String qualification, @Field("area_population") String area_population, @Field("location_type") String location_type,
            @Field("kyc1") String kyc1, @Field("kyc2") String kyc2, @Field("kyc3") String kyc3, @Field("kyc4") String kyc4,
            @Field("register") String register
    );

    //BC Registration

    //Complain
    @Multipart
    @POST("Backend/Merchant/API/app/temp/main.php?apicall=upload")
    Observable<RegularResponse> raiseAComplainTicket(@Part MultipartBody.Part proof, @Part("department") RequestBody department, @Part("transaction_id") RequestBody subject, @Part("txndate") RequestBody txndate, @Part("desc") RequestBody desc);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<SystemResponse<List<TicketHistoryModel>>> getTicketHistory(@Field("transaction_id") String transaction_id, @Field("ticketHistory") String ticketHistory);


    //Compalain//


    //Credit Card


    @FormUrlEncoded
    @POST("Backend/Merchant/API/CreditCard/PaySprint/main.php")
    Observable<CCGenerateOtpResponse> fetchCreditCardBill(@Field("card_type") String card_type,
                                                          @Field("fname") String fname, @Field("amount") String amount,
                                                          @Field("cardNum") String cardNum, @Field("mobile") String mobile,
                                                          @Field("fetchBill") String fetchBill);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/CreditCard/PaySprint/main.php")
    Observable<RegularResponse> payCreditCardBill(@Field("card_type") String card_type,
                                                  @Field("name") String name, @Field("amount") String amount,
                                                  @Field("cardnumber") String cardnumber, @Field("mobile") String mobile,
                                                  @Field("ref_id") String ref_id, @Field("otp") String otp);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/CreditCard/PaySprint/main.php")
    Observable<RegularResponse> resendOtpForRefund(@Field("ref_id") String ref_id, @Field("resendOTP") String resendOTP);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/CreditCard/PaySprint/main.php")
    Observable<RegularResponse> ccForRefund(@Field("refund_otp") String refund_otp,
                                            @Field("refund_ref_id") String refund_ref_id,
                                            @Field("refundTxn") String refundTxn);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/CreditCard/PaySprint/main.php")
    Observable<SystemResponse<List<CreditCardHistory>>> ccHistory(@Field("historyCC") String historyCC);

    //Credit Card

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/mainfund.php")
    Observable<RegularResponse> payFundAmount(
            @Field("amount") String amount,
            @Field("gatewayType") String gatewayType
    );

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/appColor.php")
    Observable<ColorModel> getAppColor(
            @Field("appColor") String appColor
    );


    //PAYU

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/sdkGateway/Payu/main.php")
    Observable<PayuResponse> getPayuCall(
            @Field("txnid") String txnid,
            @Field("amount") String amount
    );

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/sdkGateway/Payu/main.php")
    Observable<SystemResponse<String>> getHashCallHashify(@Field("hashData") String hashData);


    //PAYU

    //CASHFREE
    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/sdkGateway/Cashfree/main.php")
    Observable<SystemResponse<CashFreeLatestGateway>> addWallet(@Field("amount") String amount, @Field("addWallet") String addWallet);


    //FingpayBoard
    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/FingPay/main.php")
    Observable<OnboardPanResponse> getPanSubmitResponse(@Field("pan") String pan, @Field("long") String longi, @Field("lat") String lat);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/FingPay/main.php")
    Observable<SendOtpResp> sendFingOTP(@Field("adhar") String adhar, @Field("long") String longi, @Field("lat") String lat, @Field("ekycotp") String ekycotp);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/FingPay/main.php")
    Observable<SendOtpResp> resendFingOTP(@Field("adhar") String adhar, @Field("fptid") String fptid, @Field("pkeyid") String pkeyid, @Field("long") String longi, @Field("lat") String lat, @Field("reekycotp") String reekycotp);


    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/FingPay/main.php")
    Observable<SendOtpResp> validateFingOTP(@Field("aadhar") String adhar, @Field("otp") String otp, @Field("fptid") String fptid, @Field("pkeyid") String pkeyid, @Field("long") String longi, @Field("lat") String lat, @Field("validateOTP") String validateOTP);

    @FormUrlEncoded
    @POST("Backend/Merchant/API/AePs/FingPay/main.php")
    Observable<SendOtpResp> completeFingKyc(@Field("aadhar") String aadhar, @Field("fingerData") String fingerData, @Field("otp") String otp, @Field("fptid") String fptid, @Field("pkeyid") String pkeyid, @Field("long") String longi, @Field("lat") String lat);
    //FingpayBoard



    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<SystemResponse<List<MemberDataDto>>> getMembersList(
            @Field("userType") String userType,
            @Field("search") String search,
            @Field("membersList") String membersList
    );

    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<SystemResponse<List<MemberDataDto>>> getMyMembersList(
            @Field("oid") String oid,
            @Field("search") String search,
            @Field("myMembersList") String myMembersList
    );


    @FormUrlEncoded
    @POST("Backend/Merchant/API/app/temp/main.php")
    Observable<SystemResponse<List<MemberDataDto>>> changeMemberStatus(@Field("status") String status, @Field("userId") String userId, @Field("memberStatus") String memberStatus);


}
