package com.michau.countries.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResultEntity(
    @PrimaryKey
    val id: Int? = null,
    val mode: String,
    val points: Int,
)
