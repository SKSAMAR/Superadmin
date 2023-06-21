package com.fintech.superadmin.flight.domain

import java.util.Date

data class DateModel(
    val classicDate: String,
    val englishData: String,
    val flightDOB: String? = null,
    val time: String? = null,
    val day: String,
    val date: Date,
    val millisecond: Long
): java.io.Serializable
