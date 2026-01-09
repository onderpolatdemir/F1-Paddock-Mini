package com.onder.f1PaddockMini.features.schedule.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RaceDto(
    @SerializedName("round")
    val round: String,

    @SerializedName("race_name")
    val raceName: String,  // Backend'de "race_name", burada "raceName"

    @SerializedName("circuit_name")
    val circuitName: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("city")
    val city: String
)