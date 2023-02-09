package com.michau.countries.ui.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.michau.countries.ui.theme.BackgroundColor
import com.michau.countries.util.UiEvent

@Composable
fun ResultScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Text(text = "siema")
    }
}