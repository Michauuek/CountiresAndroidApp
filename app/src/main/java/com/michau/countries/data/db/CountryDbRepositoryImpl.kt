package com.michau.countries.data.db

import kotlinx.coroutines.flow.Flow

class CountryDbRepositoryImpl(
    private val dao: CountryDao
): CountryDbRepository {

    override suspend fun insertCountry(country: CountryEntity) {
        dao.insertCountry(country)
    }

    override suspend fun getCountryByName(name: String): CountryEntity? {
        return dao.getCountryByName(name)
    }

    override suspend fun getCountries(): Flow<List<CountryEntity>> {
        return dao.getCountries()
    }
}