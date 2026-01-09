package com.onder.f1PaddockMini.features.standings.data.mapper

import com.onder.f1PaddockMini.features.standings.data.remote.dto.TeamStandingDto
import com.onder.f1PaddockMini.features.standings.domain.model.TeamStanding

fun TeamStandingDto.toTeamStanding(): TeamStanding {
    return TeamStanding(
        position = position.toIntOrNull() ?: 99,
        teamName = teamName,
        nationality = nationality,
        points = points.toDoubleOrNull() ?: 0.0,
        wins = wins.toIntOrNull() ?: 0
    )
}