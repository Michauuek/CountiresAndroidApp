package com.michau.countries.ui.country


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun BaseCountryItem(
    name: String,
    imageUrl: String
){

    Column(
        modifier = Modifier.height(200.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "Country flag",
            modifier = Modifier.size(128.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = name)
        }
    }

}