package com.fintech.superadmin.flight.util.preferences

sealed class PathUtil(val name: String): java.io.Serializable {
    object FROM: PathUtil("From")
    object TO: PathUtil("To")
}