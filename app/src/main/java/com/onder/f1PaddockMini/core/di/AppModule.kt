package com.onder.f1PaddockMini.core.di

import com.onder.f1PaddockMini.core.common.Constants
import com.onder.f1PaddockMini.core.data.remote.F1Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class) // Uygulama yaşadığı sürece bu nesneler yaşar
object AppModule {

    @Provides
    @Singleton
    fun provideF1Api(): F1Api {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(F1Api::class.java)
    }
}