package com.onder.f1PaddockMini.features.schedule.presentation.race_detail

sealed class RaceDetailEvent {
    // Kullanıcı tepedeki Tab'lara (Race / Qualifying) tıkladığında tetiklenecek
    data class FilterChanged(val type: String) : RaceDetailEvent()
}