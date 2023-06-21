package com.fintech.superadmin.flight.util.type

sealed interface ClassType{
    object OBJECT: ClassType
    object ARRAY : ClassType
    object STRING: ClassType
    object SOMETHINELSE: ClassType
}