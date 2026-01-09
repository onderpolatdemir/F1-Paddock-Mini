package com.onder.f1PaddockMini.features.standings.domain.model

data class TeamStanding(
    val position: Int,
    val teamName: String,
    val nationality: String,
    val points: Double,
    val wins: Int
)