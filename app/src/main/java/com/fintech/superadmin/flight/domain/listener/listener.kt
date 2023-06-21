package com.fintech.superadmin.flight.domain.listener

interface MultiRoundAction{
    fun addMore()
    fun remove(index: Int)
}