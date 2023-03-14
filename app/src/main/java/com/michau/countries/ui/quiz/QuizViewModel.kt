package com.michau.countries.ui.quiz

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.db.CountryDbRepository
import com.michau.countries.data.db.CountryEntity
import com.michau.countries.data.db.ResultDbRepository
import com.michau.countries.data.db.ResultEntity
import com.michau.countries.domain.model.CountryModel
import com.michau.countries.ui.level.Levels
import com.michau.countries.util.Constants
import com.michau.countries.util.Routes
import com.michau.countries.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dbResultRepository: ResultDbRepository,
    private val dbCountryRepository: CountryDbRepository,
): ViewModel(){

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var allAnswers: MutableList<CountryEntity> by mutableStateOf(mutableListOf())
        private set

    var currentCountry: CountryEntity? by mutableStateOf(null)
        private set

    var countries: MutableList<CountryEntity> by mutableStateOf(mutableListOf())
        private set

    var points by mutableStateOf(0)
        private set

    var round by mutableStateOf(1)
        private set

    private val progressBarIncrease = ((100 / Constants.MAX_ROUND) * 0.01).toFloat()
    var progress by mutableStateOf(progressBarIncrease)

    private var mode: Levels? = null

    init {
        val gameMode = savedStateHandle.get<Levels>("level")!!
        mode = gameMode
        viewModelScope.launch {

            countries = dbCountryRepository
                .getCountries()
                .toMutableList()

            //sort countries by population
            countries.sortByDescending { countryModel ->
                countryModel.population
            }

            countries = when(gameMode){
                Levels.Easy -> {
                    countries.toList().take(50).toMutableList()
                }
                Levels.Medium -> {
                    countries.toList().drop(20).take(80).toMutableList()
                }
                Levels.Hardcore -> {
                    countries.toList().takeLast(50).toMutableList()
                }
            }
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
                        event.country.tileColor = 0xFF43A047
                        points++
                    } else {
                        /*sendUiEvent(UiEvent.ShowToast(
                            message = "Correct was ${currentCountry?.name}"
                        ))*/
                        event.country.tileColor = Red.value.toLong()

                        //Change color of correct country
                        for(answer in allAnswers){
                            if(answer.name == currentCountry?.name) {
                                answer.tileColor = 4282622023L
                            }
                        }
                        //stupid way to update UI xD
                        points--
                        points++
                    }

                    delay(1000)

                    if(round < Constants.MAX_ROUND) {
                        round++
                        progress += progressBarIncrease
                        generateNewRound()
                    } else {
                        dbResultRepository.insertCountry(
                            ResultEntity(
                                mode = mode.toString(),
                                points = points
                            )
                        )
                        sendUiEvent(UiEvent.Navigate(
                            Routes.RESULT + "?points=${points}"
                        ))
                    }
                }
            }
            else -> Unit
        }
    }
    private fun generateNewRound(){
        allAnswers.forEach { it.tileColor = White.value.toLong() }
        allAnswers = mutableStateListOf()
        selectCountry()
        generateWrongAnswers()
        allAnswers.shuffle()
    }

    private fun selectCountry(){
        countries.shuffle()
        val randomCountry = countries.random()
        countries.remove(randomCountry)
        currentCountry = randomCountry
        allAnswers.add(currentCountry!!)
    }
    private fun generateWrongAnswers(){
        val countriesForAnswers = countries.shuffled().take(3)
        allAnswers.addAll(countriesForAnswers)
    }

    private fun isAnswerCorrect(country: CountryEntity) =
        currentCountry?.name == country.name

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}