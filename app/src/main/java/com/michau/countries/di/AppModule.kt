package com.michau.countries.di

import android.app.Application
import androidx.room.Room
import com.michau.countries.data.db.ResultDatabase
import com.michau.countries.data.db.ResultDbRepository
import com.michau.countries.data.db.ResultDbRepositoryImpl
import com.michau.countries.data.remote.CountryApi
import com.michau.countries.data.remote.CountryRepository
import com.michau.countries.data.remote.CountryRepositoryImpl

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

    @Provides
    @Singleton
    fun provideResultDatabase(app: Application): ResultDatabase {
        return Room.databaseBuilder(
            app,
            ResultDatabase::class.java,
            "result_db"
        ).build()
    }
    @Provides
    @Singleton
    fun provideResultDbRepository(db: ResultDatabase): ResultDbRepository {
        return ResultDbRepositoryImpl(db.dao)
    }
}