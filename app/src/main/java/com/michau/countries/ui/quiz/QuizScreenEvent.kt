package com.michau.countries.ui.quiz

import com.michau.countries.data.db.CountryEntity

sealed class QuizScreenEvent {
    object OnNextRoundClick: QuizScreenEvent()
    data class OnAnswerClick(val country: CountryEntity): QuizScreenEvent()
    object IsLoading: QuizScreenEvent()
}
