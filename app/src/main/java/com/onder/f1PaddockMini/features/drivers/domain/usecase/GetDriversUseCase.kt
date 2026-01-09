package com.onder.f1PaddockMini.features.drivers.domain.usecase

import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.drivers.domain.model.Driver
import com.onder.f1PaddockMini.features.drivers.domain.repository.DriversRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDriversUseCase @Inject constructor(
    private val repository: DriversRepository
) {
    operator fun invoke(year: String): Flow<Resource<List<Driver>>> {
        return repository.getDrivers(year)
    }
}