package com.michau.countries.ui.country

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.db.CountryDbRepository
import com.michau.countries.data.db.CountryEntity
import com.michau.countries.data.remote.CountryRepository
import com.michau.countries.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val apiRepository: CountryRepository,
    private val dbRepository: CountryDbRepository
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
            val result = apiRepository.getRegionCountries(region)
            when(result){
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
}