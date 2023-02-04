package com.michau.countries.data.remote

import com.michau.countries.domain.country_base.CountryBase
import com.michau.countries.domain.full_details.Country
import com.michau.countries.util.Resource

interface CountryRepository {
    suspend fun getDetailsByCountryName(name: String): Resource<List<Country>>
    suspend fun getAllCountries(): Resource<List<CountryBase>>
    suspend fun getRegionCountries(region: String): Resource<List<CountryBase>>
}