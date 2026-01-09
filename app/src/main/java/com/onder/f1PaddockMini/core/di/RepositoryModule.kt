package com.onder.f1PaddockMini.core.di

import com.onder.f1PaddockMini.features.drivers.data.repository.DriversRepositoryImpl
import com.onder.f1PaddockMini.features.drivers.domain.repository.DriversRepository
import com.onder.f1PaddockMini.features.schedule.data.repository.ScheduleRepositoryImpl
import com.onder.f1PaddockMini.features.schedule.domain.repository.ScheduleRepository
import com.onder.f1PaddockMini.features.standings.data.repository.StandingsRepositoryImpl
import com.onder.f1PaddockMini.features.standings.domain.repository.StandingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // 1. Schedule
    @Binds
    @Singleton
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl
    ): ScheduleRepository

    // 2. Standings (Bunu sormuştun, EVET eklenmeli)
    @Binds
    @Singleton
    abstract fun bindStandingsRepository(
        standingsRepositoryImpl: StandingsRepositoryImpl
    ): StandingsRepository

    // 3. Drivers (Şimdi yazacağımız kısım)
    @Binds
    @Singleton
    abstract fun bindDriversRepository(
        driversRepositoryImpl: DriversRepositoryImpl
    ): DriversRepository
}