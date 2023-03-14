package com.michau.countries.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.michau.countries.data.db.*
import com.michau.countries.data.remote.CountryApi
import com.michau.countries.data.remote.CountryRepository
import com.michau.countries.data.remote.CountryRepositoryImpl
import com.michau.countries.domain.mapper.toCountryEntity

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    lateinit var countryDB: CountriesDatabase
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
    fun provideCountryApiRepository(api: CountryApi): CountryRepository {
        return CountryRepositoryImpl(api)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideResultDatabase(
        app: Application,
        api: CountryApi
    ): CountriesDatabase {
        countryDB =  Room.databaseBuilder(
            app,
            CountriesDatabase::class.java,
            "countries_db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                GlobalScope.launch(Dispatchers.IO) {
                    CountryRepositoryImpl(api)
                        .getAllCountries()
                        .data
                        ?.map {
                            it.toCountryEntity()
                        }?.let {
                            countryDB.countryDao.insertAll(
                                it
                            )
                        }
                }
            }
        }).build()

        return countryDB
    }
    @Provides
    @Singleton
    fun provideResultDbRepository(db: CountriesDatabase): ResultDbRepository {
        return ResultDbRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideCountryDbRepository(db: CountriesDatabase): CountryDbRepository {
        return CountryDbRepositoryImpl(db.countryDao)
    }
}