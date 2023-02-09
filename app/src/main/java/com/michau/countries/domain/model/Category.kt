package com.michau.countries.domain.model

import androidx.compose.ui.graphics.Color
import com.michau.countries.ui.category.CategoryEvent

data class Category(
    val name: String,
    val icon: Int,
    val onClick: CategoryEvent,
    var color: Color = Color.Yellow
)
