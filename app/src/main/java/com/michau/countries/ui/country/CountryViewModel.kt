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
        Log.d("TAG", "Featching data")
        loadCountriesList()
    }
    fun loadCountriesList(){
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )

            when(val result = repository.getAllCountries()){
                is Resource.Success -> {
                    Log.d("TAG", "ViewModel = ${result.data?.size.toString()}")
                    countries = countries.copy(
                        data = result.data!!,
                        isLoading = false,
                        error = null
                    )
                    Log.d("TAG", "ViewModel Countries = ${countries.data.size}")
                    Log.d("TAG", "ViewModel Countries = ${countries.data.get(0)}")
                    Log.d("TAG", "Success")
                }
                is Resource.Error -> {
                    countries = countries.copy(
                        data = emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                    Log.d("TAG", "Failure")
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