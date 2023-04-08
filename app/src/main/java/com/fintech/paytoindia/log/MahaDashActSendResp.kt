package com.fintech.paytoindia.log

data class MahaDashActSendResp(
    val cpid: String,
    val saltKey: String,
    val secretKey: String,
    val BcId: String,
    val UserId: String,
    val bcEmailId: String,
    val Phone1: String
){
    override fun toString(): String {
        return "MahaDashActSendResp(cpid='$cpid', saltKey='$saltKey', secretKey='$secretKey', BcId='$BcId', UserId='$UserId', bcEmailId='$bcEmailId', Phone1='$Phone1')"
    }
}



data class MahaDashActReturnResp(
    val StatusCode: String,
    val Message: String
){
    override fun toString(): String {
        return "MahaDashActReturnResp(StatusCode='$StatusCode', Message='$Message')"
    }
}