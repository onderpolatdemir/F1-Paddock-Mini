package com.onder.f1PaddockMini.features.schedule.domain.model

data class Race(
    val id: String,
    val round: String,
    val name: String,
    val date: String,
    val circuit: String,
    val location: String,
    val country: String,
    var isExpanded: Boolean = false // UI'da genişleyip genişlemediğini tutmak için
)