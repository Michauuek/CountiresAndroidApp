package com.michau.countries.ui.level

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.ui.category.CategoryEvent
import com.michau.countries.util.Routes
import com.michau.countries.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseLevelViewModel @Inject constructor(): ViewModel() {

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val levels = Levels.values().toList()
    fun onEvent(event: LevelScreenEvent){
        when(event) {
            is LevelScreenEvent.OnLevelClick ->
                sendUiEvent(UiEvent.Navigate(
                    Routes.QUIZ_GAME + "?level=${event.level}"
                ))
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}