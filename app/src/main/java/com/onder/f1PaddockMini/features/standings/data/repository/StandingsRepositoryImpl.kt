package com.onder.f1PaddockMini.features.standings.data.repository

import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.core.data.remote.F1Api
import com.onder.f1PaddockMini.features.standings.data.mapper.toTeamStanding // Mapper Importu
import com.onder.f1PaddockMini.features.standings.domain.model.TeamStanding
import com.onder.f1PaddockMini.features.standings.domain.repository.StandingsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class StandingsRepositoryImpl @Inject constructor(
    private val api: F1Api
) : StandingsRepository {

    override fun getTeamStandings(year: String): Flow<Resource<List<TeamStanding>>> = flow {
        emit(Resource.Loading(true))
        try {
            val remoteData = api.getTeamStandings(year) // F1Api'de bu metodun olduğundan emin ol!
            emit(Resource.Success(remoteData.map { it.toTeamStanding() }))
        } catch (e: IOException) {
            emit(Resource.Error("İnternet bağlantısı yok."))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Hata"))
        } finally {
            emit(Resource.Loading(false))
        }
    }
}