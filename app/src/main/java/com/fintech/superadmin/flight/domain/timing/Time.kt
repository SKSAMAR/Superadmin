package com.fintech.superadmin.flight.domain.timing

data class Time(
    val hours: Int,
    val minutes: Int,
    val journeyOfTheDay: Int,
    val twentyFourHoursTime: String,
    val twelveHoursTime: String
){
    override fun toString(): String {
        return "Time(hours=$hours, minutes=$minutes, journeyOfTheDay=$journeyOfTheDay, twentyFourHoursTime='$twentyFourHoursTime', twelveHoursTime='$twelveHoursTime')"
    }
}
