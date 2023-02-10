package com.michau.countries.ui.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
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
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Your score is: ${viewModel.points}", fontSize = 28.sp)
    }
}