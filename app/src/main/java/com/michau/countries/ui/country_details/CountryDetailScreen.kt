package com.michau.countries.ui.country_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.michau.countries.ui.theme.BackgroundColor
import com.michau.countries.util.UiEvent

@Composable
fun CountryDetailScreen(
    onPopBackStack: () -> Unit,
    viewModel: CountryDetailViewModel = hiltViewModel()
){
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "${viewModel.currentCountry?.name?.substringBefore("(")}",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )

        Image(
            painter = rememberAsyncImagePainter(viewModel.currentCountry?.flags?.png),
            contentDescription = "Country flag",
            modifier = Modifier.size(180.dp)
        )

        //rest of common country information
        Text(
            text = "Capital city: ${viewModel.currentCountry?.capital}",
            color = Color.White,
            fontSize = 24.sp
        )
        Text(
            text = "Region: ${viewModel.currentCountry?.region}",
            color = Color.White,
            fontSize = 24.sp
        )
        Text(
            text = "Subregion: ${viewModel.currentCountry?.subregion}",
            color = Color.White,
            fontSize = 24.sp
        )
        Text(
            text = "Capital city: ${viewModel.currentCountry?.capital}",
            color = Color.White,
            fontSize = 24.sp
        )
        Text(
            text = "Population: ${viewModel.currentCountry?.population}",
            color = Color.White,
            fontSize = 24.sp
        )
    }
}