package com.fintech.payware.data.qrPayement


import com.google.gson.annotations.SerializedName

data class Notes(
    @SerializedName("notes_key_1")
    val notesKey1: String,
    @SerializedName("notes_key_2")
    val notesKey2: String
)