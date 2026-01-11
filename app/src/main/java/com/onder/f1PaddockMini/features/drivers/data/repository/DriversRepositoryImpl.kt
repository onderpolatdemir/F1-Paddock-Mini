package com.onder.f1PaddockMini.features.drivers.data.repository

import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.core.data.remote.F1Api
import com.onder.f1PaddockMini.features.drivers.data.mapper.toDriver
import com.onder.f1PaddockMini.features.drivers.domain.model.Driver
import com.onder.f1PaddockMini.features.drivers.domain.repository.DriversRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class DriversRepositoryImpl @Inject constructor(
    private val api: F1Api
) : DriversRepository {

    override fun getDrivers(year: String): Flow<Resource<List<Driver>>> = flow {
        emit(Resource.Loading(true))
        try {
            val remoteData = api.getDrivers(year)
            emit(Resource.Success(remoteData.map { it.toDriver() }))
        } catch (e: UnknownHostException) {
            emit(Resource.Error("Sunucuya ulaşılamadı. Backend çalışıyor mu? (${e.message})"))
        } catch (e: SocketTimeoutException) {
            emit(Resource.Error("Bağlantı zaman aşımı. Backend yanıt vermiyor."))
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                404 -> "Endpoint bulunamadı: drivers/$year"
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