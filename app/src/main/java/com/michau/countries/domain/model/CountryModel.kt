package com.michau.countries.domain.model

import androidx.compose.ui.graphics.Color
import com.michau.countries.domain.full_details.Flags

data class CountryModel(
    var name: String = "",
    var flags: Flags,
    var region: String = "",
    var population: Int,
    var color: Color = Color.White
)
