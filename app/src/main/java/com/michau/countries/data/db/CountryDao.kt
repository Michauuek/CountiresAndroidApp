package com.michau.countries.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<CountryEntity>)

    @Insert
    suspend fun insertCountry(country: CountryEntity)

    @Query("SELECT * FROM countries WHERE name = :name")
    suspend fun getCountryByName(name: String): CountryEntity?

    @Query("SELECT * FROM countries")
    fun getCountries(): Flow<List<CountryEntity>>
}