package com.michau.countries.ui.country

import com.michau.countries.data.db.CountryEntity


data class CountriesListState(
    val data: List<CountryEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
