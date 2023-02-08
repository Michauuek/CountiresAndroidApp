package com.michau.countries.ui.quiz

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.remote.CountryRepository
import com.michau.countries.domain.mapper.toCountryModel
import com.michau.countries.domain.model.CountryModel
import com.michau.countries.util.Constants
import com.michau.countries.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val apiRepository: CountryRepository
): ViewModel(){

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var allAnswers: MutableList<CountryModel> by mutableStateOf(mutableListOf())
        private set

    var currentCountry: CountryModel? by mutableStateOf(null)
        private set

    var countries: MutableList<CountryModel> by mutableStateOf(mutableListOf())
        private set

    var points by mutableStateOf(0)
        private set

    var round by mutableStateOf(1)
        private set

    var progress by mutableStateOf(0.1f)


    init {
        viewModelScope.launch {
            countries = (apiRepository.getAllCountries().data?.map {
                it.toCountryModel() } ?: emptyList()).toMutableList()

            generateNewRound()
        }
    }
    fun onEvent(event: QuizScreenEvent){
        when(event) {
            is QuizScreenEvent.OnNextRoundClick -> {
                viewModelScope.launch {
                    generateNewRound()
                }
            }
            is QuizScreenEvent.OnAnswerClick -> {

                viewModelScope.launch {
                    if(isAnswerCorrect(event.country)) {
                        event.country.color = Green
                        points++
                    } else {
                        sendUiEvent(UiEvent.ShowToast(
                            message = "Correct was ${currentCountry?.name}"
                        ))
                        event.country.color = Red
                    }

                    delay(2000)

                    if(round < Constants.MAX_ROUND) {
                        round++
                        progress += 0.1f
                        generateNewRound()
                    }
                }
            }
            else -> Unit
        }
    }
    private fun generateNewRound(){
        allAnswers.forEach { it.color = White }
        allAnswers = mutableListOf()
        selectCountry()
        generateWrongAnswers()
        allAnswers.shuffle()
    }

    private fun selectCountry(){
        val randomCountry = countries.random()
        countries.remove(randomCountry)
        currentCountry = randomCountry
        allAnswers.add(currentCountry!!)
    }
    private fun generateWrongAnswers(){
        val countriesForAnswers = countries.shuffled().take(3)
        allAnswers.addAll(countriesForAnswers)
    }

    private fun isAnswerCorrect(country: CountryModel) =
        currentCountry?.name == country.name

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun cleanTiles(){
        for(i in 1..4) {
            sendUiEvent(UiEvent.ChangeAnswerColor(Color.White))
        }
    }
}