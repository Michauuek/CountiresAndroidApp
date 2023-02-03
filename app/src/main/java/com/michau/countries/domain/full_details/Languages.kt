package com.michau.countries.domain.full_details

import com.squareup.moshi.Json


data class Languages (
    @field:Json(name ="iso639_1")
    var iso6391 : String? = null,

    @field:Json(name ="iso639_2")
    var iso6392 : String? = null,

    @field:Json(name ="name")
    var name : String? = null,

    @field:Json(name ="nativeName")
    var nativeName : String? = null
)
