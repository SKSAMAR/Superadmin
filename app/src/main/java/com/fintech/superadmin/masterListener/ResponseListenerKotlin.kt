package com.fintech.superadmin.masterListener

interface ResponseListenerKotlin<in T> {
    fun loading()
    fun success(data: T)
    fun failure(error: String)
}