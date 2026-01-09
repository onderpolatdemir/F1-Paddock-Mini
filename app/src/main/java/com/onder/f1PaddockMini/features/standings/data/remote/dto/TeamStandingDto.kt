package com.onder.f1PaddockMini.features.standings.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TeamStandingDto(
    @SerializedName("position")
    val position: String,

    @SerializedName("team_name")
    val teamName: String,

    @SerializedName("nationality")
    val nationality: String,

    @SerializedName("points")
    val points: String,

    @SerializedName("wins")
    val wins: String
)