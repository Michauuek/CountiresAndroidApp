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

    var points by mutableStateOf(0)
        private set

    init {
        points = savedStateHandle.get<Int>("points")!!
        Log.d("TAG", points.toString())
        viewModelScope.launch {
            dbRepository.getBestResults().collect {
                Log.d("TAG", it.toString())
                results.addAll(it)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}