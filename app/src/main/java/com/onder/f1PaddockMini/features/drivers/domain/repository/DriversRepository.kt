package com.onder.f1PaddockMini.features.drivers.domain.repository
import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.drivers.domain.model.Driver
import kotlinx.coroutines.flow.Flow

interface DriversRepository {
    fun getDrivers(year: String): Flow<Resource<List<Driver>>>
}