package com.onder.f1PaddockMini.features.schedule.data.repository

import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.core.data.remote.F1Api
import com.onder.f1PaddockMini.features.schedule.data.mapper.toQualifyingResult
import com.onder.f1PaddockMini.features.schedule.domain.model.Race
import com.onder.f1PaddockMini.features.schedule.domain.repository.ScheduleRepository
import com.onder.f1PaddockMini.features.schedule.data.mapper.toRace
import com.onder.f1PaddockMini.features.schedule.data.mapper.toRaceResult
import com.onder.f1PaddockMini.features.schedule.domain.model.QualifyingResult
import com.onder.f1PaddockMini.features.schedule.domain.model.RaceResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.collections.map
import kotlin.collections.mapNotNull

class ScheduleRepositoryImpl @Inject constructor(
    private val api: F1Api
) : ScheduleRepository {

    override fun getSchedule(year: String): Flow<Resource<List<Race>>> = flow {
        emit(Resource.Loading(true))
        try {
            val remoteData = api.getSchedule(year)
            val domainData = remoteData.map { it.toRace() }
            emit(Resource.Success(domainData))
        } catch (e: UnknownHostException) {
            emit(Resource.Error("Sunucuya ulaşılamadı. Backend çalışıyor mu? (${e.message})"))
        } catch (e: SocketTimeoutException) {
            emit(Resource.Error("Bağlantı zaman aşımı. Backend yanıt vermiyor."))
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                404 -> "Endpoint bulunamadı: schedule/$year"
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

    override fun getRaceResults(year: String, round: String): Flow<Resource<List<RaceResult>>> = flow {
        emit(Resource.Loading(true))
        try {
            val remoteData = api.getRaceResults(year, round)
            emit(Resource.Success(remoteData.map { it.toRaceResult() }))
        } catch (e: UnknownHostException) {
            emit(Resource.Error("Sunucuya ulaşılamadı. Backend çalışıyor mu? (${e.message})"))
        } catch (e: SocketTimeoutException) {
            emit(Resource.Error("Bağlantı zaman aşımı. Backend yanıt vermiyor."))
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                404 -> "Endpoint bulunamadı: results/$year/$round"
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

    override fun getQualifyingResults(year: String, round: String): Flow<Resource<List<QualifyingResult>>> = flow {
        emit(Resource.Loading(true))
        try {
            val remoteData = api.getQualifyingResults(year, round)
            emit(Resource.Success(remoteData.map { it.toQualifyingResult() }))
        } catch (e: HttpException) {
            // 404 durumunda boş liste döndür (bazı eski yarışlarda Qualifying datası olmayabilir)
            if (e.code() == 404) {
                emit(Resource.Success(emptyList()))
            } else {
                emit(Resource.Error("HTTP Hatası ${e.code()}: ${e.message()}"))
            }
        } catch (e: UnknownHostException) {
            emit(Resource.Error("Sunucuya ulaşılamadı. Backend çalışıyor mu? (${e.message})"))
        } catch (e: SocketTimeoutException) {
            emit(Resource.Error("Bağlantı zaman aşımı. Backend yanıt vermiyor."))
        } catch (e: IOException) {
            emit(Resource.Error("Ağ hatası: ${e.message ?: "İnternet bağlantısı yok"}"))
        } catch (e: Exception) {
            // Bazı eski yarışlarda Qualifying datası olmayabilir, boş liste dönelim
            emit(Resource.Success(emptyList()))
        }
    }


}
