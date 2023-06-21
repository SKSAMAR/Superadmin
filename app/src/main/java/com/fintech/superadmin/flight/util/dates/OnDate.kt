package com.fintech.superadmin.flight.util.dates

sealed class OnDate(val name: String) {
    object Today: OnDate("Today")
    object Tomorrow: OnDate("Tomorrow")
    object PreviousDay: OnDate("Previous Day")
    object NextDay: OnDate("Next Day")
}

val currentDayBadges = listOf(
    OnDate.Today,
    OnDate.Tomorrow
)

val allDayBadges = listOf(
    OnDate.Today,
    OnDate.Tomorrow,
    OnDate.NextDay,
    OnDate.PreviousDay
)

val anotherDayBadges = listOf(
    OnDate.NextDay,
    OnDate.PreviousDay,
)