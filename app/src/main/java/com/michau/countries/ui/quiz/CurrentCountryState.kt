package com.michau.countries.ui.quiz

import com.michau.countries.data.db.CountryEntity

data class CurrentCountryState(
    val data: CountryEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
