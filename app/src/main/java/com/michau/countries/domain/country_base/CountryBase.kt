package com.michau.countries.domain.country_base


import com.michau.countries.domain.full_details.Flags
import com.squareup.moshi.Json

data class CountryBase(
    @field:Json(name ="name")
    var name: String = "",

    @field:Json(name ="flags")
    var flags: Flags,

    @field:Json(name ="region")
    var region: String = "",

    @field:Json(name ="population")
    var population: Int,
)
