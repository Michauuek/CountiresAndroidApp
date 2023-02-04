package com.michau.countries.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryEntity(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val flagUrl: String,
    val region: String,
    val population: Int
)
