package com.onder.f1PaddockMini.features.standings.domain.usecase

import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.standings.domain.model.TeamStanding
import com.onder.f1PaddockMini.features.standings.domain.repository.StandingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamStandingsUseCase @Inject constructor(
    private val repository: StandingsRepository
) {
    operator fun invoke(year: String): Flow<Resource<List<TeamStanding>>> {
        return repository.getTeamStandings(year)
    }
}