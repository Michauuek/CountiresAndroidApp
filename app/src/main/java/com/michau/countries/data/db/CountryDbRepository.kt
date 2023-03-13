package com.michau.countries.data.db

import kotlinx.coroutines.flow.Flow

interface CountryDbRepository {
    suspend fun insertCountry(country: CountryEntity)
    suspend fun getCountryByName(name: String): CountryEntity?
    suspend fun getCountries(): Flow<List<CountryEntity>>
}