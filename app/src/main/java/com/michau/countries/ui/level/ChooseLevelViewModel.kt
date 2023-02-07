package com.michau.countries.ui.level

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseLevelViewModel @Inject constructor(): ViewModel() {
    val levels = Levels.values().toList()
}