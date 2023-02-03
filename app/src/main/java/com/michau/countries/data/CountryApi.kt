package com.michau.countries.data

import com.michau.countries.domain.Country
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApi {
    @GET("/v2/name/{name}")
    suspend fun getCountryData(@Path("name") name: String): List<Country>
}