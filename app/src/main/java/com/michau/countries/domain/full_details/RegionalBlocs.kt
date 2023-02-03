package com.michau.countries.domain.full_details

import com.squareup.moshi.Json


data class RegionalBlocs (
    @field:Json(name ="acronym")
    var acronym: String? = null,

    @field:Json(name ="name")
    var name: String? = null
)
