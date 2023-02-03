package com.michau.countries.ui.country

import com.michau.countries.domain.country_base.CountryBase


data class CountriesListState(
    val data: List<CountryBase> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
