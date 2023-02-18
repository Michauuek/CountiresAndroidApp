package com.michau.countries.ui.country_border

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.michau.countries.util.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun CountryBorderScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: CountryBorderViewModel = hiltViewModel()
){


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Log.d("TAG", "file:///android_asset/countries_outline/all/${viewModel.currentCountry?.alpha2Code}/256.png\"")
        AsyncImage(
            model = "file:///android_asset/countries_outline/all/" +
                    "${viewModel.currentCountry?.alpha2Code.toString().lowercase()}/512.png",
            contentDescription = "",
        )
    }
}