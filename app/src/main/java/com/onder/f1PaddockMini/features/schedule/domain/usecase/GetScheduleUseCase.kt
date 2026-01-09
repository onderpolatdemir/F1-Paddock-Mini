package com.onder.f1PaddockMini.features.schedule.domain.usecase

import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.schedule.domain.model.QualifyingResult
import com.onder.f1PaddockMini.features.schedule.domain.model.Race
import com.onder.f1PaddockMini.features.schedule.domain.model.RaceResult
import com.onder.f1PaddockMini.features.schedule.domain.repository.ScheduleRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow


class GetScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    operator fun invoke(year: String): Flow<Resource<List<Race>>> {
        // İleride burada "Sadece Avrupa yarışlarını getir" gibi filtreleme yapabilirsin
        return repository.getSchedule(year)
    }
}