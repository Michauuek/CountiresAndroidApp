package com.michau.countries.di

import com.michau.countries.data.CountryApi
import com.michau.countries.data.CountryRepository
import com.michau.countries.data.CountryRepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCountryApi(): CountryApi {
        return Retrofit.Builder()
            .baseUrl("https://restcountries.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(api: CountryApi): CountryRepository {
        return CountryRepositoryImpl(api)
    }
}