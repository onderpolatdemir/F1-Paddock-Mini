package com.onder.f1PaddockMini.features.standings.presentation

sealed class StandingsEvent {
    data class YearChanged(val year: String) : StandingsEvent()
}