package com.michau.countries.ui.country_border

import com.michau.countries.domain.model.CountryModel

sealed class BorderScreenEvent {
    object OnNextRoundClick: BorderScreenEvent()
    data class OnAnswerClick(val country: CountryModel): BorderScreenEvent()
}
