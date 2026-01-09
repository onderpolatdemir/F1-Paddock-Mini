package com.onder.f1PaddockMini.features.drivers.presentation

import com.onder.f1PaddockMini.features.drivers.domain.model.Driver

data class DriversState(
    val drivers: List<Driver> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val selectedYear: String = "2024"
)