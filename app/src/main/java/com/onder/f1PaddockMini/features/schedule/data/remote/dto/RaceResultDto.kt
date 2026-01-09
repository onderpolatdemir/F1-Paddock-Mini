package com.onder.f1PaddockMini.features.schedule.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RaceResultDto(
    @SerializedName("position")
    val position: String,

    @SerializedName("driver_name")
    val driverName: String,

    @SerializedName("team_name")
    val teamName: String,

    @SerializedName("grid")
    val grid: String,

    @SerializedName("laps")
    val laps: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("time")
    val time: String,

    @SerializedName("points")
    val points: String
)