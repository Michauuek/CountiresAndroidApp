package com.michau.countries.ui.result

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.db.ResultDbRepository
import com.michau.countries.data.db.ResultEntity
import com.michau.countries.ui.level.LevelScreenEvent
import com.michau.countries.util.Routes
import com.michau.countries.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dbRepository: ResultDbRepository
): ViewModel() {

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val results = mutableListOf<ResultEntity>()

    var points by mutableStateOf("")
        private set

    init {
        points = savedStateHandle.get<String>("points")!!

        viewModelScope.launch {
            dbRepository.getBestResults().collect {
                results.addAll(it)
            }
        }
    }

    fun onEvent(event: ResultScreenEvent){
        when(event) {
            is ResultScreenEvent.OnHomeButtonClick ->
                sendUiEvent(UiEvent.Navigate(
                    Routes.CATEGORY
                ))
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}