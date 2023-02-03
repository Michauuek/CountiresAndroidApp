package com.michau.countries.domain.full_details

import com.squareup.moshi.Json


data class Flags (
    @field:Json(name ="svg")
    var svg : String = "",

    @field:Json(name ="png")
    var png : String = ""
)
