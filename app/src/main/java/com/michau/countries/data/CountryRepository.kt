package com.michau.countries.data

import com.michau.countries.domain.Country
import com.michau.countries.util.Resource

interface CountryRepository {
    suspend fun getDetailsByCountryName(name: String): Resource<List<Country>>
}