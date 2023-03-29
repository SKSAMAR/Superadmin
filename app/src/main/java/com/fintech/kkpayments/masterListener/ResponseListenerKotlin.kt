package com.fintech.kkpayments.masterListener

interface ResponseListenerKotlin<in T> {
    fun loading()
    fun success(data: T)
    fun failure(error: String)
}