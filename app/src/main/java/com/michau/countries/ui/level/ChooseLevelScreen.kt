package com.michau.countries.ui.level


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.michau.countries.ui.theme.BackgroundColor
import com.michau.countries.ui.theme.Purple500
import com.michau.countries.util.UiEvent

@Composable
fun ChooseLevelScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ChooseLevelViewModel = hiltViewModel()
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
            .background(BackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Choose difficulty level",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White
                )
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn {
                items(viewModel.levels) { level ->
                    LevelButton(
                        color = Purple500,
                        text = level.toString(),
                        modifier = Modifier
                            .padding(12.dp)
                            .size(120.dp, 55.dp),
                        onClick = {
                            viewModel.onEvent(LevelScreenEvent.OnLevelClick(level))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LevelButton(
    color: Color,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(color),
        modifier = modifier
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold
        )
    }
}