package com.michau.countries.domain.mapper

import com.michau.countries.data.db.CountryEntity
import com.michau.countries.domain.country_base.CountryBase
import com.michau.countries.domain.full_details.Country
import com.michau.countries.domain.model.CountryModel


fun CountryBase.toCountryModel() =
    CountryModel(
        name = this.name,
        region = this.region,
        flags = this.flags,
        population = this.population,
        alpha2Code = this.alpha2Code,
        latlng = this.latlng,
        borders = this.borders
    )

fun Country.toCountryModel() =
    CountryModel(
        name = this.name ?: "",
        region = this.region ?: "",
        flags = this.flags!!,
        population = this.population,
        alpha2Code = this.alpha2Code ?: "",
        latlng = this.latlng,
        borders = this.borders
    )

fun CountryBase.toCountryEntity() =
    CountryEntity(
        name = this.name,
        capital = this.capital ?: "Unknown",
        flag = this.flags.png,
        subregion = this.subregion,
        currency = /*this.currencies.firstOrNull()?.name ?:*/ "Unknown",
        currencySymbol = /*this.currencies.firstOrNull()?.symbol ?:*/ "Unknown",
        region = this.region,
        population = this.population,
        alpha2Code = this.alpha2Code,
        latitude = /*this.latlng.first()*/ 0.0,
        longitude = /*this.latlng.last()*/ 0.0,
    )