package com.michau.countries.ui.country

import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun CountrySearch(
    viewModel: CountryViewModel = hiltViewModel()
){

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TextField(
            value = viewModel.countryName,
            onValueChange = {
                viewModel.countryName = it
            }
        )
        Button(onClick = {
            viewModel.loadCountryInfo(viewModel.countryName)
        }) {
            Text(text = "Search")
        }

        if(viewModel.state.country != null){
            Text(text = viewModel.state.country!!.capital!!)
        }

        val regions = listOf("Europe", "Africa", "Americas", "Asia", "Oceania", "Europe", "Africa", "Americas", "Asia", "Oceania")
        LazyRow(modifier = Modifier.fillMaxWidth()){
            items(regions){
                Card(
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .padding(5.dp)
                        .clickable {
                            viewModel.loadRegionCountries(it)
                        },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(it)
                }
            }
        }

        Text(text = "Countries List")

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(viewModel.countries.data) { country ->
                BaseCountryItem(country.name, country.flags.png)
            }
        }


    }
}