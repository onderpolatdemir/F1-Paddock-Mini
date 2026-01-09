package com.onder.f1PaddockMini.features.schedule.data.mapper

import com.onder.f1PaddockMini.features.schedule.data.remote.dto.RaceDto
import com.onder.f1PaddockMini.features.schedule.domain.model.Race
import com.onder.f1PaddockMini.features.schedule.data.remote.dto.RaceResultDto
import com.onder.f1PaddockMini.features.schedule.data.remote.dto.QualifyingDto
import com.onder.f1PaddockMini.features.schedule.domain.model.RaceResult
import com.onder.f1PaddockMini.features.schedule.domain.model.QualifyingResult

fun RaceDto.toRace(): Race {
    return Race(
        id = round,
        name = raceName,       // Artık race_name değil, raceName
        date = date,
        circuit = circuitName, // Artık circuit_name değil, circuitName
        location = "$city, $country" // Şehir ve Ülkeyi birleştirdik
    )
}

fun RaceResultDto.toRaceResult(): RaceResult {
    return RaceResult(
        position = position.toIntOrNull() ?: 99,
        driverName = driverName,
        teamName = teamName,
        time = time,
        points = points.toDoubleOrNull() ?: 0.0,
        grid = grid.toIntOrNull() ?: 0,
        laps = laps.toIntOrNull() ?: 0,
        status = status
    )
}

fun QualifyingDto.toQualifyingResult(): QualifyingResult {
    return QualifyingResult(
        position = position.toIntOrNull() ?: 99,
        driverName = driverName,
        teamName = teamName,
        q1 = q1 ?: "-",
        q2 = q2 ?: "-",
        q3 = q3 ?: "-"
    )
}