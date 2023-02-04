package com.michau.countries.ui.country

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.CountryRepository
import com.michau.countries.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository
): ViewModel() {

    var state by mutableStateOf(CountryState())
        private set

    var countries by mutableStateOf(CountriesListState())
        private set

    var countryName by mutableStateOf("")

    init {
        loadCountriesList()
    }

    fun loadRegionCountries(region: String){
        viewModelScope.launch {
            countries = countries.copy(
                isLoading = true,
                error = null
            )
            when(val result = repository.getRegionCountries(region)){
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
            when(val result = repository.getAllCountries()){
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

            when(val result = repository.getDetailsByCountryName(name)){
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
}