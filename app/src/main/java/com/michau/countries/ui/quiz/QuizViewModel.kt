package com.michau.countries.ui.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.remote.CountryRepository
import com.michau.countries.domain.country_base.CountryBase
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

    var answersState by mutableStateOf(AnswersState())
        private set

    var currentCountry by mutableStateOf(CurrentCountryState())
        private set

    var countries: MutableList<CountryBase> by mutableStateOf(mutableListOf())
        private set

    var points by mutableStateOf(0)
        private set

    var round by mutableStateOf(1)
        private set

    var progress by mutableStateOf(0.1f)


    init {
        viewModelScope.launch {
            countries = (apiRepository.getAllCountries().data ?: emptyList()).toMutableList()
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
                       sendUiEvent(UiEvent.ShowToast(message = "Correct"))
                       points++
                   } else {
                       sendUiEvent(UiEvent.ShowToast(message = "Wrong answer"))
                   }
                    delay(1000)

                    //send to UI white color of tile
                    cleanTiles()

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
        answersState = answersState.copy(data = mutableListOf())
        selectCountry()
        generateWrongAnswers()
        answersState.data.shuffle()
    }

    private fun selectCountry(){
        val randomCountry = countries.random()
        countries.remove(randomCountry)
        currentCountry = currentCountry.copy(
            data = randomCountry,
            isLoading = false,
            error = null
        )
        answersState.data.add(currentCountry.data!!)
    }
    private fun generateWrongAnswers(){
        val countriesForAnswers = countries.shuffled().take(3)
        answersState.data.addAll(countriesForAnswers)
    }

    fun isAnswerCorrect(country: CountryBase) =
        currentCountry.data?.name == country.name

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