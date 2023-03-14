package com.michau.countries.ui.country

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.db.CountryDbRepository
import com.michau.countries.util.Routes
import com.michau.countries.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val dbRepository: CountryDbRepository
): ViewModel() {

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var countries by mutableStateOf(CountriesListState())
        private set

    var isLoading by mutableStateOf(true)
        private set

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

            countries = countries.copy(
                data = dbRepository.getCountriesByRegion(region),
                isLoading = false,
                error = null
            )
        }
    }
    private fun loadCountriesList(){
        viewModelScope.launch {

            Log.d("TAG", dbRepository.getCountries().count().toString())

            countries = countries.copy(
                data = dbRepository.getCountries(),
                isLoading = false,
                error = null
            )
            isLoading = false
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}