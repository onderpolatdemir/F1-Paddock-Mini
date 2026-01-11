package com.onder.f1PaddockMini.features.standings.data.repository

import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.core.data.remote.F1Api
import com.onder.f1PaddockMini.features.standings.data.mapper.toTeamStanding
import com.onder.f1PaddockMini.features.standings.domain.model.TeamStanding
import com.onder.f1PaddockMini.features.standings.domain.repository.StandingsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class StandingsRepositoryImpl @Inject constructor(
    private val api: F1Api
) : StandingsRepository {

    override fun getTeamStandings(year: String): Flow<Resource<List<TeamStanding>>> = flow {
        emit(Resource.Loading(true))
        try {
            val remoteData = api.getTeamStandings(year)
            emit(Resource.Success(remoteData.map { it.toTeamStanding() }))
        } catch (e: UnknownHostException) {
            emit(Resource.Error("Sunucuya ulaşılamadı. Backend çalışıyor mu? (${e.message})"))
        } catch (e: SocketTimeoutException) {
            emit(Resource.Error("Bağlantı zaman aşımı. Backend yanıt vermiyor."))
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                404 -> "Endpoint bulunamadı: standings/teams/$year"
                500 -> "Sunucu hatası (500)"
                else -> "HTTP Hatası ${e.code()}: ${e.message()}"
            }
            emit(Resource.Error(errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Ağ hatası: ${e.message ?: "İnternet bağlantısı yok"}"))
        } catch (e: Exception) {
            emit(Resource.Error("Beklenmedik hata: ${e.javaClass.simpleName} - ${e.message}"))
        }
    }
}