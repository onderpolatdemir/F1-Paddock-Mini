package com.onder.f1PaddockMini.features.schedule.domain.usecase

import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.schedule.domain.model.QualifyingResult
import com.onder.f1PaddockMini.features.schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQualifyingResultUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    operator fun invoke(year: String, round: String): Flow<Resource<List<QualifyingResult>>> {
        return repository.getQualifyingResults(year, round)
    }
}