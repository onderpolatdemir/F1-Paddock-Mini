package com.onder.f1PaddockMini.features.drivers.data.mapper


import com.onder.f1PaddockMini.features.drivers.data.remote.dto.DriverDto
import com.onder.f1PaddockMini.features.drivers.domain.model.Driver

fun DriverDto.toDriver(): Driver {
    return Driver(
        id = driverId,
        name = driverName,
        number = driverNumber.toIntOrNull() ?: 0, // N/A ise boş göster
        nationality = nationality,
        team = teamName,
        position = position.toIntOrNull() ?: 99,
        points = points.toDoubleOrNull() ?: 0.0
    )
}