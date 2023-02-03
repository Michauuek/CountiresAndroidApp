package com.michau.countries.data

import com.michau.countries.domain.Country
import com.michau.countries.util.Resource
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val api: CountryApi
): CountryRepository {

    override suspend fun getDetailsByCountryName(name: String): Resource<List<Country>> {
        return try {
            Resource.Success(
                data = api.getCountryData(
                    name = name
                )
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}