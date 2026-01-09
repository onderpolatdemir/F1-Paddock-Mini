package com.onder.f1PaddockMini.features.drivers.presentation

sealed class DriversEvent {
    data class YearChanged(val year: String) : DriversEvent()
}