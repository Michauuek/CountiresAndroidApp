package com.michau.countries.ui.country_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.michau.countries.data.db.CountryDbRepository
import com.michau.countries.data.db.CountryEntity
import com.michau.countries.data.remote.CountryRepository
import com.michau.countries.domain.full_details.Country
import com.michau.countries.util.UiEvent
import com.squareup.moshi.JsonDataException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToLong

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    private val dbRepository: CountryDbRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var currentCountry: CountryEntity? by mutableStateOf(null)
        private set

    var population by mutableStateOf("")
        private set

    var currency by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        val countryName = savedStateHandle.get<String>("countryName")!!

        if(countryName.isNotBlank()) {
            viewModelScope.launch {

                try{
                    currentCountry = dbRepository.getCountryByName(countryName)
                } catch (e: Error){
                    sendUiEvent(UiEvent.PopBackStack)
                }

                population = if((currentCountry?.population ?: 0) > 1000000) {
                    "${(currentCountry?.population)?.times(0.000001)?.roundToLong()} mln"
                } else {
                    "${currentCountry?.population}"
                }

                currency = try {
                    "${currentCountry?.currency} " +
                            "${currentCountry?.currencySymbol}"
                } catch (e: Exception) {
                    "Unknown"
                }

                isLoading = false
            }
        }
    }

    fun onEvent(event: CountryDetailEvent) {
        when(event) {
            is CountryDetailEvent.OnReturnClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.PopBackStack)
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