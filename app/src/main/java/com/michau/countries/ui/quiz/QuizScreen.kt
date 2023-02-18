@file:OptIn(ExperimentalFoundationApi::class)

package com.michau.countries.ui.quiz

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.michau.countries.domain.model.CountryModel
import com.michau.countries.ui.theme.BackgroundColor
import com.michau.countries.util.UiEvent

@Composable
fun QuizScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect {event ->
            when(event) {
                is UiEvent.ShowToast ->
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is UiEvent.Navigate ->
                    onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            LinearProgressIndicator(
                progress = viewModel.progress,
                modifier = Modifier.padding(top = 42.dp),
                color = Color(0xFF2E7D32)
            )


            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Round ${viewModel.round}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            Text(
                text = "Points ${viewModel.points}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = rememberAsyncImagePainter(viewModel.currentCountry?.flags?.png),
                contentDescription = "Country flag",
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(viewModel.allAnswers) { country ->
                    AnswerTile(
                        name = country.name.substringBefore("("),
                        modifier = Modifier
                            .padding(10.dp)
                            .height(80.dp)
                            .clickable {
                                viewModel.onEvent(QuizScreenEvent.OnAnswerClick(country))
                            },
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
    country: CountryModel
){
    Card(
        modifier = modifier,
        border = BorderStroke(2.dp, Color.Black),
        backgroundColor = country.color,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = name,
                maxLines = 2,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}