package com.onder.f1PaddockMini.features.schedule.data.remote.dto

import com.google.gson.annotations.SerializedName

data class QualifyingDto(
    @SerializedName("position")
    val position: String,

    @SerializedName("driver_name")
    val driverName: String,

    @SerializedName("team_name")
    val teamName: String,

    @SerializedName("q1")
    val q1: String?,

    @SerializedName("q2")
    val q2: String?,

    @SerializedName("q3")
    val q3: String?
)