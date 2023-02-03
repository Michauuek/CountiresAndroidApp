package com.michau.countries.domain

import com.squareup.moshi.Json


data class Flags (
    @field:Json(name ="svg")
    var svg : String? = null,

    @field:Json(name ="png")
    var png : String? = null
)
