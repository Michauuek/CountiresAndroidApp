package com.michau.countries.data

import com.michau.countries.domain.country_base.CountryBase
import com.michau.countries.domain.full_details.Country
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApi {
    @GET("/v2/name/{name}")
    suspend fun getCountryData(@Path("name") name: String): List<Country>

    @GET("/v2/all")
    suspend fun getAllCountriesBase(): List<CountryBase>

    @GET("/v2/region/{region}")
    suspend fun getCountriesFromRegion(@Path("region") region: String): List<CountryBase>
}