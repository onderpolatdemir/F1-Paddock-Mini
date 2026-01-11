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

    // 1. Adım: Hilt'e Retrofit nesnesini nasıl oluşturacağını öğretin.
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 2. Adım: Hilt'in oluşturduğu Retrofit nesnesini parametre olarak alın.
    @Provides
    @Singleton
    fun provideF1Api(retrofit: Retrofit): F1Api {
        return retrofit.create(F1Api::class.java)
    }
}