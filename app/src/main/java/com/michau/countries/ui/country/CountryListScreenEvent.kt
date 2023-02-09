package com.michau.countries.ui.country

sealed class CountryListScreenEvent{
    data class OnCountryClick(val countryName: String): CountryListScreenEvent()
}
