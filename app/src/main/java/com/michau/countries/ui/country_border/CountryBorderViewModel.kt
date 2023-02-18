package com.michau.countries.ui.country_border

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.db.ResultEntity
import com.michau.countries.data.remote.CountryRepository
import com.michau.countries.domain.country_base.CountryBase
import com.michau.countries.domain.full_details.Country
import com.michau.countries.domain.mapper.toCountryModel
import com.michau.countries.domain.model.CountryModel
import com.michau.countries.ui.quiz.QuizScreenEvent
import com.michau.countries.util.Constants
import com.michau.countries.util.Routes
import com.michau.countries.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryBorderViewModel @Inject constructor(
    private val apiRepository: CountryRepository
): ViewModel(){

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var currentCountry: CountryModel? by mutableStateOf(null)
        private set

    var countries: MutableList<CountryModel> by mutableStateOf(mutableListOf())
        private set


    init {
        viewModelScope.launch {
            countries = (apiRepository.getAllCountries().data?.map {
                it.toCountryModel() } ?: emptyList()).toMutableList()

            countries.shuffle()
            currentCountry = countries.random()

            Log.d("TAG", currentCountry?.alpha2Code.toString())
            Log.d("TAG", currentCountry.toString())
        }
    }
    fun onEvent(event: BorderScreenEvent){
        when(event) {
            is BorderScreenEvent.OnNextRoundClick -> {

            }
            else -> Unit
        }
    }


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}