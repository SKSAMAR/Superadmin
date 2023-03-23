package com.fintech.payware.masterListener

interface ResponseListenerKotlin<in T> {
    fun loading()
    fun success(data: T)
    fun failure(error: String)
}