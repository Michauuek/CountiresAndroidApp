package com.michau.countries.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Insert
    suspend fun insertCountry(country: CountryEntity)

    @Query("SELECT * FROM countryentity WHERE name = :name")
    suspend fun getCountryByName(name: String): CountryEntity?

    @Query("SELECT * FROM countryentity WHERE region = :region")
    fun getCountriesByRegion(region: String): Flow<List<CountryEntity>>

    @Query("SELECT * FROM countryentity")
    fun getCountries(): Flow<List<CountryEntity>>
}