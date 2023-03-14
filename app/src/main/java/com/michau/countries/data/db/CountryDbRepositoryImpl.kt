package com.michau.countries.data.db

class CountryDbRepositoryImpl(
    private val dao: CountryDao
): CountryDbRepository {

    override suspend fun insertCountry(country: CountryEntity) {
        dao.insertCountry(country)
    }

    override suspend fun getCountryByName(name: String): CountryEntity? {
        return dao.getCountryByName(name)
    }

    override suspend fun getCountries(): List<CountryEntity> {
        return dao.getCountries()
    }

    override suspend fun getCountriesByRegion(region: String): List<CountryEntity> {
        return dao.getCountriesByRegion(region)
    }
}