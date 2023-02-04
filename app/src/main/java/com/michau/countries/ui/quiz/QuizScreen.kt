@file:OptIn(ExperimentalFoundationApi::class)

package com.michau.countries.ui.quiz

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Country")

        Button(onClick = {
            viewModel.onEvent(QuizScreenEvent.OnNextRoundClick)
        }) {
            Text("Start")
        }

        Image(
            painter = rememberAsyncImagePainter(viewModel.currentCountry.data?.flagUrl),
            contentDescription = "Country flag",
            modifier = Modifier.size(128.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(viewModel.answersState.data) { country ->
                AnswerTile(
                    name = country.name,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                        viewModel.onEvent(QuizScreenEvent.OnNextRoundClick)
                    }
                )
            }
        }
    }
}

@Composable
fun AnswerTile(
    name: String,
    modifier: Modifier = Modifier
){
    Card(modifier = modifier) {
        Text(text = name)
    }
}