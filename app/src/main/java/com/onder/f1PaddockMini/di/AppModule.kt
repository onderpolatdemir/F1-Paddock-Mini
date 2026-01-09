package com.onder.f1PaddockMini.di

import com.onder.f1PaddockMini.core.data.remote.F1Api
import com.onder.f1PaddockMini.features.drivers.data.repository.DriversRepositoryImpl
import com.onder.f1PaddockMini.features.drivers.domain.repository.DriversRepository
import com.onder.f1PaddockMini.features.schedule.data.repository.ScheduleRepositoryImpl
import com.onder.f1PaddockMini.features.schedule.domain.repository.ScheduleRepository
import com.onder.f1PaddockMini.features.standings.data.repository.StandingsRepositoryImpl
import com.onder.f1PaddockMini.features.standings.domain.repository.StandingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Emulator için localhost: 10.0.2.2
    // Gerçek cihaz kullanıyorsan bilgisayarının yerel IP'sini yaz (örn: 192.168.1.35)
    private const val BASE_URL = "http://10.0.2.2:8000/"

    @Provides
    @Singleton
    fun provideF1Api(): F1Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(F1Api::class.java)
    }

    @Provides
    @Singleton
    fun provideDriversRepository(api: F1Api): DriversRepository {
        return DriversRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(api: F1Api): ScheduleRepository {
        return ScheduleRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun proivdeStandingsRepository(api: F1Api): StandingsRepository {
        return StandingsRepositoryImpl(api)
    }

}