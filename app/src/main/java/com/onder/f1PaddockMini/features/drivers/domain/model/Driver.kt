package com.onder.f1PaddockMini.features.drivers.domain.model

data class Driver(
    val id: String,
    val name: String,
    val number: Int,
    val nationality: String,
    val team: String,
    val position: Int,
    val points: Double
)