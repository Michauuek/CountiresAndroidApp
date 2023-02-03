package com.michau.countries.data

import android.util.Log
import com.michau.countries.domain.country_base.CountryBase
import com.michau.countries.domain.full_details.Country
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

    override suspend fun getAllCountries(): Resource<List<CountryBase>> {
        Log.d("TAG", api.getAllCountriesBase().size.toString())
        return try {
            Resource.Success(
                data = api.getAllCountriesBase()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}