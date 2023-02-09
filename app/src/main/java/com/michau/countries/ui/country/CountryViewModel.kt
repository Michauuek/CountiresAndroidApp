package com.michau.countries.ui.country

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.remote.CountryRepository
import com.michau.countries.ui.quiz.QuizScreenEvent
import com.michau.countries.util.Resource
import com.michau.countries.util.Routes
import com.michau.countries.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val apiRepository: CountryRepository
): ViewModel() {

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(CountryState())
        private set

    var countries by mutableStateOf(CountriesListState())
        private set

    var countryName by mutableStateOf("")

    init {
        loadCountriesList()
    }

    fun onEvent(event: CountryListScreenEvent){
        when(event) {
            is CountryListScreenEvent.OnCountryClick -> {
                sendUiEvent(UiEvent.Navigate(
                    Routes.COUNTRY_DETAIL + "?countryName=${event.countryName}"
                ))
            }
        }
    }

    fun loadRegionCountries(region: String){
        viewModelScope.launch {
            countries = countries.copy(
                isLoading = true,
                error = null
            )
            when(val result = apiRepository.getRegionCountries(region)){
                is Resource.Success -> {
                    countries = countries.copy(
                        data = result.data!!,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    countries = countries.copy(
                        data = emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
    private fun loadCountriesList(){
        viewModelScope.launch {
            when(val result = apiRepository.getAllCountries()){
                is Resource.Success -> {
                    countries = countries.copy(
                        data = result.data!!,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    countries = countries.copy(
                        data = emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun loadCountryInfo(name: String){
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )

            when(val result = apiRepository.getDetailsByCountryName(name)){
                is Resource.Success -> {
                    state = state.copy(
                        country = result.data?.first(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        country = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}