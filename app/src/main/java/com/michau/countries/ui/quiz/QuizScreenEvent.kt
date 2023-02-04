package com.michau.countries.ui.quiz

sealed class QuizScreenEvent {
    object OnNextRoundClick: QuizScreenEvent()
    object isLoading: QuizScreenEvent()
}
