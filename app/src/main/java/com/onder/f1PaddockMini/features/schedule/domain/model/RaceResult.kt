package com.onder.f1PaddockMini.features.schedule.domain.model

    data class RaceResult(
        val position: Int,
        val driverName: String,
        val teamName: String,
        val grid: Int,
        val laps: Int,
        val time: String,
        val points: Double,
        val status: String
    )