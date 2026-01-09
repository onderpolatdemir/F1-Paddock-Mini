package com.onder.f1PaddockMini.features.schedule.domain.model

data class Race(
    val id: String,
    val name: String,
    val date: String,
    val circuit: String,
    val location: String,
    var isExpanded: Boolean = false // UI'da genişleyip genişlemediğini tutmak için
)