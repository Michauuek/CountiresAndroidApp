package com.michau.countries.ui.quiz

import com.michau.countries.data.db.CountryEntity

data class AnswersState(
    val data: MutableList<CountryEntity> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)
