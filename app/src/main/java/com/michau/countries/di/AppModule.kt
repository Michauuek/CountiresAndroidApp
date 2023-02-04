package com.michau.countries.di

import android.app.Application
import androidx.room.Room
import com.michau.countries.data.db.CountryDatabase
import com.michau.countries.data.db.CountryDbRepository
import com.michau.countries.data.db.CountryDbRepositoryImpl
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
    fun provideTodoRepository(api: CountryApi, db: CountryDatabase): CountryRepository {
        return CountryRepositoryImpl(api, CountryDbRepositoryImpl(db.dao))
    }

    @Provides
    @Singleton
    fun provideCountryDatabase(app: Application): CountryDatabase {
        return Room.databaseBuilder(
            app,
            CountryDatabase::class.java,
            "country_db"
        ).build()
    }
    @Provides
    @Singleton
    fun provideCountryDbRepository(db: CountryDatabase): CountryDbRepository {
        return CountryDbRepositoryImpl(db.dao)
    }
}