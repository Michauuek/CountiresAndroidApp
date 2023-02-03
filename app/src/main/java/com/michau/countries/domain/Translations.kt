package com.michau.countries.domain
import com.squareup.moshi.Json


data class Translations (
    @field:Json(name ="br")
    var br: String? = null,

    @field:Json(name ="pt")
    var pt: String? = null,

    @field:Json(name ="nl")
    var nl: String? = null,

    @field:Json(name ="hr")
    var hr: String? = null,

    @field:Json(name ="fa")
    var fa: String? = null,

    @field:Json(name ="de")
    var de: String? = null,

    @field:Json(name ="es")
    var es: String? = null,

    @field:Json(name ="fr")
    var fr: String? = null,

    @field:Json(name ="ja")
    var ja: String? = null,

    @field:Json(name ="it")
    var it: String? = null,

    @field:Json(name ="hu")
    var hu: String? = null

)
