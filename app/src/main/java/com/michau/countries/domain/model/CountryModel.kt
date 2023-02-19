package com.michau.countries.domain.model

import androidx.compose.ui.graphics.Color
import com.michau.countries.domain.full_details.Flags

data class CountryModel(
    val name: String = "",
    val flags: Flags,
    val region: String = "",
    val population: Int,
    val alpha2Code: String = "",
    val latlng: List<Double>,
    val borders: List<String>,
    var color: Color = Color.White,
)
