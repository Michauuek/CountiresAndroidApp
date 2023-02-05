package com.michau.countries.ui.quiz


import com.michau.countries.domain.country_base.CountryBase

data class CurrentCountryState(
    val data: CountryBase? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
