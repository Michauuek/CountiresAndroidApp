package com.michau.countries.data.db

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    var id: Int? = null,
    var name: String,
    var region: String,
    var population: Int,
    var alpha2Code: String,
) {
    constructor() : this(0, "", "", 0, "")
}