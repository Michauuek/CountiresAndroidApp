package com.michau.countries.ui.country

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
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
            .padding(top = 20.dp)
    ) {

        if(!viewModel.isLoading){
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(UiConstants.regions) {
                    Card(
                        modifier = Modifier
                            .background(BackgroundColor)
                            .padding(horizontal = 6.dp, vertical = 8.dp)
                            .size(width = 80.dp, height = 40.dp)
                            .clickable {
                                viewModel.loadRegionCountries(it)
                            },
                        elevation = 20.dp,
                        contentColor = BackgroundColor,
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = Color.White
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it,
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(viewModel.countries.data) { country ->
                    BaseCountryItem(
                        country.name.substringBefore("("),
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
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                CircularProgressIndicator(
                    color = Color(0xFF2E7D32)
                )
            }
        }
    }
}