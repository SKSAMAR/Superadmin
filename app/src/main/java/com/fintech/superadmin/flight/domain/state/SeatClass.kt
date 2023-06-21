package com.fintech.superadmin.flight.domain.state

sealed class SeatClass(val title: String, val id: Int) {
    object Economy : SeatClass(title = "Economy", id = 0)
    object Business : SeatClass(title = "Business", id = 1)
    object FirstClass : SeatClass(title = "First Class", id = 2)
    object Premium : SeatClass(title = "Premium Economy", id = 3)
}

var seatClassesList = listOf(
    SeatClass.Economy,
    SeatClass.Business,
    SeatClass.FirstClass,
    SeatClass.Premium,
)

