package com.michau.countries.ui.country

import com.michau.countries.domain.Country

data class CountryState(
    val country: Country? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
