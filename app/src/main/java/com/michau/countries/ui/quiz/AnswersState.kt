package com.michau.countries.ui.quiz

import com.michau.countries.domain.country_base.CountryBase

data class AnswersState(
    val data: MutableList<CountryBase> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)
