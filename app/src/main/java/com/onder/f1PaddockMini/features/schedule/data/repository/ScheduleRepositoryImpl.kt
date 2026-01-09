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
import java.io.IOException
import kotlin.collections.map
import kotlin.collections.mapNotNull

class ScheduleRepositoryImpl @Inject constructor(
    private val api: F1Api
) : ScheduleRepository {

    override fun getSchedule(year: String): Flow<Resource<List<Race>>> = flow {
        emit(Resource.Loading(true)) // 1. Yükleniyor...
        try {
            val remoteData = api.getSchedule(year) // API isteği
            val domainData = remoteData.map { it.toRace() } // Dönüştürme
            emit(Resource.Success(domainData)) // 2. Başarılı
        } catch (e: IOException) {
            emit(Resource.Error("Sunucuya ulaşılamadı. Docker açık mı?"))
        } catch (e: Exception) {
            emit(Resource.Error("Hata: ${e.localizedMessage}"))
        } finally {
            emit(Resource.Loading(false)) // 3. Yükleme bitti
        }
    }

    override fun getRaceResults(year: String, round: String): Flow<Resource<List<RaceResult>>> = flow {
        emit(Resource.Loading(true))
        try {
            val remoteData = api.getRaceResults(year, round)
            emit(Resource.Success(remoteData.map { it.toRaceResult() }))
        } catch (e: IOException) {
            emit(Resource.Error("Bağlantı hatası."))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Beklenmedik hata"))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    override fun getQualifyingResults(year: String, round: String): Flow<Resource<List<QualifyingResult>>> = flow {
        emit(Resource.Loading(true))
        try {
            val remoteData = api.getQualifyingResults(year, round)
            emit(Resource.Success(remoteData.map { it.toQualifyingResult() }))
        } catch (e: Exception) {
            // Bazı eski yarışlarda Qualifying datası olmayabilir, boş liste dönelim
            emit(Resource.Success(emptyList()))
        } finally {
            emit(Resource.Loading(false))
        }
    }


}
