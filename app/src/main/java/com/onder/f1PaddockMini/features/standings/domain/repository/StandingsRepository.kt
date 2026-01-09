package com.onder.f1PaddockMini.features.standings.domain.repository

import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.standings.domain.model.TeamStanding
import kotlinx.coroutines.flow.Flow

interface StandingsRepository {
    fun getTeamStandings(year: String): Flow<Resource<List<TeamStanding>>>
    // Eğer sürücü puan durumunu da ekleyeceksen buraya getDriverStandings() eklemelisin.
}