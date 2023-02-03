package com.michau.countries.ui.country

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

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

        Text(text = "Countries List")

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(viewModel.countries.data) { country ->
                BaseCountryItem(country.name, country.flags.png)
            }
        }


    }
}