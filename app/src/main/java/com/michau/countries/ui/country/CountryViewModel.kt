package com.michau.countries.ui.country

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

    var countryName by mutableStateOf("")

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