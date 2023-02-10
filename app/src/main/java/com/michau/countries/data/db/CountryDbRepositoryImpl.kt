package com.michau.countries.data.db

import kotlinx.coroutines.flow.Flow

class CountryDbRepositoryImpl(
    private val dao: CountryDao
): CountryDbRepository {

    override suspend fun insertCountry(country: ResultEntity) {
        dao.insertCountry(country)
    }

    override suspend fun getCountryByName(name: String): ResultEntity? {
        return dao.getCountryByName(name)
    }

    override fun getCountriesByRegion(region: String): Flow<List<ResultEntity>> {
        return dao.getCountriesByRegion(region);
    }

    override fun getCountries(): Flow<List<ResultEntity>> {
        return dao.getCountries()
    }
}