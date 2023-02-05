package com.michau.countries.ui.quiz

import com.michau.countries.domain.country_base.CountryBase

sealed class QuizScreenEvent {
    object OnNextRoundClick: QuizScreenEvent()
    data class OnAnswerClick(val country: CountryBase): QuizScreenEvent()
    object IsLoading: QuizScreenEvent()
}
