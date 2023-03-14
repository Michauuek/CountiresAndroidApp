package com.michau.countries.ui.country_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.michau.countries.ui.theme.BackgroundColor
import com.michau.countries.util.UiEvent
import kotlin.math.roundToInt

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
            .background(BackgroundColor)
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(viewModel.isLoading){
            CircularProgressIndicator(
                color = Color(0xFF2E7D32)
            )
        }
        else{
            Text(
                text = "${viewModel.currentCountry?.name?.substringBefore("(")}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = rememberAsyncImagePainter(viewModel.currentCountry?.flag),
                contentDescription = "Country flag",
                modifier = Modifier
                    .size(180.dp)
                    .padding(15.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Capital city: ${viewModel.currentCountry?.capital ?: "No capital"}",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(3.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Region: ${viewModel.currentCountry?.region}",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(3.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Subregion: ${viewModel.currentCountry?.subregion}",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(3.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Currency: ${viewModel.currency}",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(3.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Population: ${viewModel.population}",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(3.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}