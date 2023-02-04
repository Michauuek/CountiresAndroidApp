package com.michau.countries.ui.quiz

import androidx.lifecycle.ViewModel
import com.michau.countries.data.remote.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: CountryRepository
): ViewModel(){

}