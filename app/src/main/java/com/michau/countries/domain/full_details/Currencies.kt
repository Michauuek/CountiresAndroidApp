package com.michau.countries.domain.full_details

import com.squareup.moshi.Json


data class Currencies (
    @field:Json(name ="code")
    var code : String? = null,

    @field:Json(name ="name")
    var name : String? = null,

    @field:Json(name ="symbol")
    var symbol : String? = null
)
