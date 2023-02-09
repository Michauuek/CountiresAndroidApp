package com.michau.countries.ui.country

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.michau.countries.ui.theme.BackgroundColor
import com.michau.countries.util.UiConstants
import com.michau.countries.util.UiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountrySearch(
    viewModel: CountryViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
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

        Text(
            modifier = Modifier.padding(20.dp),
            text = "Countries List",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )


        LazyRow(
            modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor)
        ){
            items(UiConstants.regions){
                Card(
                    modifier = Modifier
                        .background(BackgroundColor)
                        .padding(5.dp)
                        .size(width = 100.dp, height = 60.dp)
                        .clickable {
                            viewModel.loadRegionCountries(it)
                        },
                    contentColor = BackgroundColor,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = it,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(viewModel.countries.data) { country ->
                BaseCountryItem(
                    country.name,
                    country.flags.png,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(CountryListScreenEvent.OnCountryClick(country.name))
                        }
                )
            }
        }
    }
}