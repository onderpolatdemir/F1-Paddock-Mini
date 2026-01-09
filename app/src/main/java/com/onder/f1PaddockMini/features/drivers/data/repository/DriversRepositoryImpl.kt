package com.onder.f1PaddockMini.features.drivers.data.repository

import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.core.data.remote.F1Api
import com.onder.f1PaddockMini.features.drivers.data.mapper.toDriver // Mapper importunu unutma!
import com.onder.f1PaddockMini.features.drivers.domain.model.Driver
import com.onder.f1PaddockMini.features.drivers.domain.repository.DriversRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class DriversRepositoryImpl @Inject constructor(
    private val api: F1Api
) : DriversRepository {

    override fun getDrivers(year: String): Flow<Resource<List<Driver>>> = flow {
        emit(Resource.Loading(true))
        try {
            val remoteData = api.getDrivers(year) // F1Api'de getDrivers var mı kontrol et
            emit(Resource.Success(remoteData.map { it.toDriver() }))
        } catch (e: IOException) {
            emit(Resource.Error("İnternet bağlantısı yok."))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Hata oluştu"))
        } finally {
            emit(Resource.Loading(false))
        }
    }
}