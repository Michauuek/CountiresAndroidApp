package com.michau.countries.data.db

import kotlinx.coroutines.flow.Flow

interface CountryDbRepository {
    suspend fun insertCountry(country: CountryEntity)
    suspend fun getCountryByName(name: String): CountryEntity?
    fun getCountriesByRegion(region: String): Flow<List<CountryEntity>>
    fun getCountries(): Flow<List<CountryEntity>>
}