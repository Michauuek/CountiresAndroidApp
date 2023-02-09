package com.michau.countries.domain.full_details

import com.squareup.moshi.Json


data class Country (

    @field:Json(name ="name")
    var name: String? = null,

    @field:Json(name ="topLevelDomain")
    var topLevelDomain: List<String> = arrayListOf(),

    @field:Json(name ="alpha2Code")
    var alpha2Code: String? = null,

    @field:Json(name ="alpha3Code")
    var alpha3Code: String? = null,

    @field:Json(name ="callingCodes")
    var callingCodes: List<String> = arrayListOf(),

    @field:Json(name ="capital")
    var capital: String? = null,

    @field:Json(name ="altSpellings")
    var altSpellings: List<String> = arrayListOf(),

    @field:Json(name ="subregion")
    var subregion: String? = null,

    @field:Json(name ="region")
    var region: String? = null,

    @field:Json(name ="population")
    var population: Int? = null,

    @field:Json(name ="demonym")
    var demonym: String? = null,

    @field:Json(name ="area")
    var area: Int? = null,

    @field:Json(name ="gini")
    var gini: Double? = null,

    @field:Json(name ="timezones")
    var timezones: List<String> = arrayListOf(),

    @field:Json(name ="borders")
    var borders: List<String> = arrayListOf(),

    @field:Json(name ="nativeName")
    var nativeName: String? = null,

    @field:Json(name ="numericCode")
    var numericCode: String? = null,

    @field:Json(name ="flags")
    var flags: Flags? = Flags(),

    @field:Json(name ="currencies")
    var currencies: List<Currencies> = arrayListOf(),

    @field:Json(name ="languages")
    var languages: List<Languages> = arrayListOf(),

    @field:Json(name ="translations")
    var translations: Translations? = Translations(),

    @field:Json(name ="flag")
    var flag: String? = null,

    @field:Json(name ="regionalBlocs")
    var regionalBlocs: List<RegionalBlocs> = arrayListOf(),

    @field:Json(name ="cioc")
    var cioc: String? = null,

    @field:Json(name ="independent")
    var independent: Boolean? = null
)


