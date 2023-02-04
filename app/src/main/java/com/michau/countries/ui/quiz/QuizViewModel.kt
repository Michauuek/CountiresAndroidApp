package com.michau.countries.ui.quiz

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.db.CountryDbRepository
import com.michau.countries.data.db.CountryEntity
import com.michau.countries.data.remote.CountryRepository
import com.michau.countries.ui.country.CountriesListState
import com.michau.countries.util.Resource
import com.michau.countries.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val apiRepository: CountryRepository,
    private val dbRepository: CountryDbRepository
): ViewModel(){

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var answersState by mutableStateOf(AnswersState())
        private set

    var currentCountry by mutableStateOf(CurrentCountryState())
        private set

    var countries: List<CountryEntity> by mutableStateOf(emptyList())
        private set


    init {
        viewModelScope.launch {

            currentCountry = currentCountry.copy(
                data = null,
                isLoading = true,
                error = null
            )

            /*countries = dbRepository.getCountries().toList().first()
            Log.d("TAG", countries.size.toString())*/

            /*if(countries.isEmpty()){
                countries = apiRepository.getAllCountries().data?.map {
                    CountryEntity(
                        name = it.name,
                        region = it.region,
                        population = it.population,
                        flagUrl = it.flags.png
                    )
                } ?: emptyList()
            }*/

            countries = apiRepository.getAllCountries().data?.map {
                CountryEntity(
                    name = it.name,
                    region = it.region,
                    population = it.population,
                    flagUrl = it.flags.png
                )
            } ?: emptyList()

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
        }
    }
    private fun generateNewRound(){
        answersState = answersState.copy(
            data = mutableListOf()
        )
        selectCountry()
        generateWrongAnswers()
        answersState.data.shuffle()
    }

    private fun selectCountry(){
        currentCountry = currentCountry.copy(
            data = countries.random(),
            isLoading = false,
            error = null
        )
        answersState.data.add(currentCountry.data!!)
    }
    private fun generateWrongAnswers(){
        val countriesForAnswers = countries.shuffled().take(3)
        answersState.data.addAll(countriesForAnswers)
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}