package com.michau.countries.ui.category

sealed class CategoryEvent {
    object OnQuizCategoryClick: CategoryEvent()
    object OnCountryCategoryClick: CategoryEvent()
    object OnCountryShapeClick: CategoryEvent()
}