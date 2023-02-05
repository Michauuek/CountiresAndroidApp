@file:OptIn(ExperimentalFoundationApi::class)

package com.michau.countries.ui.quiz

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.michau.countries.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect {event ->
            when(event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is UiEvent.Navigate -> TODO()
                is UiEvent.PopBackStack -> TODO()
                is UiEvent.ShowSnackbar -> TODO()
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
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
                painter = rememberAsyncImagePainter(viewModel.currentCountry.data?.flags?.png),
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
                                viewModel.onEvent(QuizScreenEvent.OnAnswerClick(country))
                        }
                    )
                }
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