package com.michau.countries.util

import androidx.compose.ui.graphics.Color

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent()
    data class ChangeAnswerColor(val color: Color): UiEvent()
    data class ShowToast(val message: String): UiEvent()
}
