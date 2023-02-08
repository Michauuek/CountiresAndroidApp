package com.michau.countries.ui.quiz

import com.michau.countries.domain.model.CountryModel

sealed class QuizScreenEvent {
    object OnNextRoundClick: QuizScreenEvent()
    data class OnAnswerClick(val country: CountryModel): QuizScreenEvent()
    object IsLoading: QuizScreenEvent()
}
