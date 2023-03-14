package com.michau.countries.util

import com.michau.countries.R
import com.michau.countries.domain.model.Category
import com.michau.countries.ui.category.CategoryEvent

object UiConstants {
    val regions = listOf("Europe", "Africa", "Americas", "Asia", "Oceania", "All")
    val categories = listOf(
        Category("Countries", R.drawable.globe, CategoryEvent.OnCountryCategoryClick),
        Category("Quiz", R.drawable.quiz, CategoryEvent.OnQuizCategoryClick),
        Category("Shape", R.drawable.shape, CategoryEvent.OnCountryShapeClick)
    )
}