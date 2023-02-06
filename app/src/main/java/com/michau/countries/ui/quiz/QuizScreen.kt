@file:OptIn(ExperimentalFoundationApi::class)

package com.michau.countries.ui.quiz

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.michau.countries.domain.country_base.CountryBase
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
                else -> Unit
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

            LinearProgressIndicator(progress = viewModel.progress)

            Text("Round ${viewModel.round}")
            Text("Points ${viewModel.points}")

            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = rememberAsyncImagePainter(viewModel.currentCountry.data?.flags?.png),
                contentDescription = "Country flag",
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(100.dp))

            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(viewModel.answersState.data) { country ->
                    AnswerTile(
                        name = country.name.substringBefore("("),
                        modifier = Modifier
                            .padding(10.dp)
                            .height(80.dp),
                        country = country
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerTile(
    name: String,
    modifier: Modifier = Modifier,
    country: CountryBase,
    viewModel: QuizViewModel = hiltViewModel()
){
    var backgroundColor by remember { mutableStateOf(Color.White) }

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect {event ->
            when(event) {
                is UiEvent.ChangeAnswerColor -> {
                    backgroundColor = event.color
                }
                else -> Unit
            }
        }
    }

    Button(
        modifier = modifier,
        border = BorderStroke(2.dp, Color.Black),
        colors = ButtonDefaults.buttonColors(backgroundColor),
        onClick = {
            viewModel.onEvent(QuizScreenEvent.OnAnswerClick(country))
            backgroundColor = if(viewModel.isAnswerCorrect(country)) Color.Green else Color.Red
        }
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = name, maxLines = 2)
        }
    }
}