package com.michau.countries.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    var id: Int? = null,
    var name: String,
    var capital: String?,
    var region: String,
    var subregion: String,
    var currency: String?,
    var currencySymbol: String?,
    var flag: String,
    var population: Int,
    var alpha2Code: String,
    var latitude: Double?,
    var longitude: Double?,
    var tileColor: Long = 0xFFFFFFFF,
) {
    constructor() : this(
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        "",
        0.0,
        0.0,
        0xFFFFFFFF
    )
}