package com.michau.countries.data.db

import kotlinx.coroutines.flow.Flow

interface CountryDbRepository {
    suspend fun insertCountry(country: ResultEntity)
    suspend fun getCountryByName(name: String): ResultEntity?
    fun getCountriesByRegion(region: String): Flow<List<ResultEntity>>
    fun getCountries(): Flow<List<ResultEntity>>
}