package com.michau.countries.ui.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.michau.countries.ui.level.LevelButton
import com.michau.countries.ui.level.LevelScreenEvent
import com.michau.countries.ui.theme.BackgroundColor
import com.michau.countries.util.UiEvent

@Composable
fun ResultScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
){

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Your score is: ${viewModel.points}",
            fontSize = 28.sp,
            color = Color.White
        )

        LevelButton(
            color = Color(0xFF2E7D32),
            text = "Home",
            onClick = {
                viewModel.onEvent(ResultScreenEvent.OnHomeButtonClick)
            }
        )
    }
}