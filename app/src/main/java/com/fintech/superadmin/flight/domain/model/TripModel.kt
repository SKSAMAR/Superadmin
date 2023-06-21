package com.fintech.superadmin.flight.domain.model

data class TripModel(
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return ""+name
    }
}