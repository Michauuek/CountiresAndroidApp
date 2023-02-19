package com.michau.countries.ui.country_border

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michau.countries.data.remote.CountryRepository
import com.michau.countries.domain.mapper.toCountryModel
import com.michau.countries.domain.model.CountryModel
import com.michau.countries.util.Constants.AVERAGE_RADIUS_OF_EARTH_KM
import com.michau.countries.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.*

@HiltViewModel
class CountryBorderViewModel @Inject constructor(
    private val apiRepository: CountryRepository
): ViewModel(){

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var currentCountry: CountryModel? by mutableStateOf(null)
        private set

    var countries: MutableList<CountryModel> by mutableStateOf(mutableListOf())
        private set


    init {
        viewModelScope.launch {
            countries = (apiRepository.getAllCountries().data?.map {
                it.toCountryModel() } ?: emptyList()).toMutableList()

            countries.shuffle()
            currentCountry = countries.random()

            Log.d("TAG", currentCountry.toString())
        }
    }
    fun onEvent(event: BorderScreenEvent){
        when(event) {
            is BorderScreenEvent.OnAnswerClick -> {
                viewModelScope.launch {
                    val guessedCountry = apiRepository
                        .getDetailsByCountryName(event.country)
                        .data?.first()?.toCountryModel()

                    val distance = try {
                        calculateDistanceInKilometer(
                            currentCountry?.latlng?.component1()!!,
                            currentCountry?.latlng?.component2()!!,
                            guessedCountry?.latlng?.component1()!!,
                            guessedCountry.latlng.component2()
                        )
                    } catch (e: IndexOutOfBoundsException) {
                        null
                    }

                    if (distance == 0) {
                        sendUiEvent(UiEvent.ShowToast("You've guessed correctly"))
                    } else {
                        sendUiEvent(UiEvent.ShowToast("Wrong answer - $distance"))
                    }
                }
            }
            else -> Unit
        }
    }

    /**
     *  Function to calculate distance between 2 locations
     *  using latitude and longitude
     *
     *  Math formula
     *  =acos(sin(lat1)*sin(lat2)+cos(lat1)*cos(lat2)*cos(lon2-lon1))*6371
     *  (6371 is Earth radius in km.)
     */
    private fun calculateDistanceInKilometer(
        userLat: Double, userLng: Double,
        venueLat: Double, venueLng: Double
    ): Int {
        val latDistance = Math.toRadians(userLat - venueLat)
        val lngDistance = Math.toRadians(userLng - venueLng)

        val a = (sin(latDistance / 2) * sin(latDistance / 2)
                + (cos(Math.toRadians(userLat)) * cos(Math.toRadians(venueLat))
                * sin(lngDistance / 2) * sin(lngDistance / 2)))

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return (AVERAGE_RADIUS_OF_EARTH_KM * c).roundToInt()
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}