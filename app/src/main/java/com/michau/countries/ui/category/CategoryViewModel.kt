package com.michau.countries.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.ui.country.CountryListScreenEvent
import com.michau.countries.util.Routes
import com.michau.countries.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(): ViewModel() {

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CategoryEvent){
        when(event) {
            is CategoryEvent.OnQuizCategoryClick ->
                sendUiEvent(UiEvent.Navigate(Routes.QUIZ_LEVEL))
            is CategoryEvent.OnCountryCategoryClick ->
                sendUiEvent(UiEvent.Navigate(Routes.COUNTRIES_LIST))
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}