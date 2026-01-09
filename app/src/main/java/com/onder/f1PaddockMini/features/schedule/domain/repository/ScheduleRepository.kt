package com.onder.f1PaddockMini.features.schedule.domain.repository

import com.onder.f1PaddockMini.core.common.Resource
import com.onder.f1PaddockMini.features.schedule.domain.model.QualifyingResult
import com.onder.f1PaddockMini.features.schedule.domain.model.Race
import com.onder.f1PaddockMini.features.schedule.domain.model.RaceResult
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getSchedule(year: String): Flow<Resource<List<Race>>>
    fun getRaceResults(year: String, round: String): Flow<Resource<List<RaceResult>>>
    fun getQualifyingResults(year: String, round: String): Flow<Resource<List<QualifyingResult>>>
}