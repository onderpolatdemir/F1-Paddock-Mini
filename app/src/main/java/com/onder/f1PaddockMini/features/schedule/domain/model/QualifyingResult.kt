package com.onder.f1PaddockMini.features.schedule.domain.model

data class QualifyingResult(
    val position: Int,
    val driverName: String,
    val teamName: String,
    val q1: String,
    val q2: String,
    val q3: String
)