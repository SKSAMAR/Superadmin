package com.fintech.superadmin.flight.data.remote

import com.fintech.superadmin.flight.data.remote.dto.comm.CommPackDto
import com.fintech.superadmin.flight.data.remote.dto.AirPortDto
import com.fintech.superadmin.flight.data.remote.dto.AirlineDto
import com.fintech.superadmin.flight.data.remote.dto.airFareRule.AirFareRuleDto
import com.fintech.superadmin.flight.data.remote.dto.airSearch.AirSearchDto
import com.fintech.superadmin.flight.data.remote.dto.passenger.PassengerInfoDto
import com.fintech.superadmin.flight.data.remote.dto.seatMap.AirSeatMapDto
import com.fintech.superadmin.flight.data.remote.dto.ssr.SsrDto
import com.fintech.superadmin.flight.data.remote.dto.system.SystemResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FlightApi {

    @FormUrlEncoded
    @POST("Backend/Merchant/API/travel/main.php")
    fun getAirports(
        @Field("search") search: String,
        @Field("getAirports") getAirports: String = "getAirports"
    ): Observable<SystemResponse<List<AirPortDto?>?>>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/travel/main.php")
    fun getAirlines(
        @Field("search") search: String,
        @Field("getAirlines") getAirports: String = "getAirlines"
    ): Observable<SystemResponse<List<AirlineDto>>>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/travel/fetch_air.php")
    fun getAirSearch(
        @Field("BookableData") BookableData: String,
    ): Observable<AirSearchDto>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/travel/main.php")
    fun getAirFareRules(
        @Field("skey") skey: String,
        @Field("fkey") fkey: String,
        @Field("fareid") fareid: String,
        @Field("getAirFareRules") getAirFareRules: String = "getAirFareRules"
    ): Observable<AirFareRuleDto>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/travel/main.php")
    fun getPassengers(
        @Field("fname") fname: String,
        @Field("lname") lname: String,
        @Field("getPassenger") getPassenger: String = "getPassenger"
    ): Observable<SystemResponse<List<PassengerInfoDto>>>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/travel/main.php")
    fun getSSR(
        @Field("skey") skey: String,
        @Field("fkey") fkey: String,
        @Field("fareid") fareid: String,
        @Field("getSSR") getSSR: String = "getSSR"
    ): Observable<SsrDto>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/travel/main.php")
    fun getSeats(
        @Field("skey") skey: String,
        @Field("fkey") fkey: String,
        @Field("fareid") fareid: String,
        @Field("seatData") seatData: String,
    ): Observable<AirSeatMapDto>


    @FormUrlEncoded
    @POST("Backend/Merchant/API/travel/main.php")
    fun getCommissionInfo(
        @Field("travelTypeAmounts") travelTypeAmounts: Int?,
    ): Observable<SystemResponse<CommPackDto?>>


}